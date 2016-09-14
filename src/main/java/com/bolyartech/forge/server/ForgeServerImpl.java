package com.bolyartech.forge.server;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.bolyartech.forge.server.config.ServerConfiguration;
import com.bolyartech.forge.server.config.ServerConfigurationImpl;
import com.bolyartech.forge.server.module.ForgeModule;
import com.bolyartech.forge.server.module.ModuleRegister;
import com.bolyartech.forge.server.module.ModuleRegisterImpl;
import com.google.common.base.Strings;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import static spark.Spark.*;


abstract public class ForgeServerImpl implements ForgeServer {
    private static final String LOG_FILE_PREFIX = "forge_server";
    private static final String CONFIG_DIR_SYSTEM_PROPERY_NAME = "forgeServerConfigDir";
    private static final String SERVER_CONFIG_FILE = "server.conf";


    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private ServerConfiguration mServerConfiguration;

    private final ModuleRegister mModuleRegister = new ModuleRegisterImpl();

    private File mConfigDir;


    @Override
    public void start() {
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());

        mServerConfiguration = loadConfig();
        initLog(mServerConfiguration.getLogBackConfigFile(), mServerConfiguration.getServerNameSuffix());

        initHttpServer(mServerConfiguration);

        mLogger.info("Server started.");
    }


    protected void registerModule(ForgeModule mod) {
        mModuleRegister.registerModule(mod);
    }


    private void initHttpServer(ServerConfiguration conf) {
        if (conf.getHttpPort() > 0) {
            port(conf.getHttpPort());
        }

        if (conf.getKeyStorePath() != null && !conf.getKeyStorePath().equals("")) {
            secure(conf.getKeyStorePath(), conf.getKeyStorePassword(), null, null);
            mLogger.info("Will use HTTPS");
        }
    }


    private ServerConfiguration loadConfig() {
        mConfigDir = findConfigDirectory();
        if (mConfigDir != null) {
            File serverConf = new File(mConfigDir, SERVER_CONFIG_FILE);
            try {
                try (FileInputStream is = new FileInputStream(serverConf)) {
                    Properties prop = new Properties();
                    prop.load(is);
                    return new ServerConfigurationImpl(prop.getProperty("server_name_suffix"),
                            prop.getProperty("logback_config_file"),
                            Integer.parseInt(prop.getProperty("http_port")),
                            Integer.parseInt(prop.getProperty("session_timeout")),
                            prop.getProperty("keystore_path"),
                            prop.getProperty("keystore_password")
                    );
                }
            } catch (IOException e) {
                mLogger.error("Cannot read " + serverConf.getAbsolutePath());
                return null;
            }
        } else {
            return null;
        }
    }


    private File findConfigDirectory() {
        // first we check if system property is defined
        String configDirPath = System.getProperty(CONFIG_DIR_SYSTEM_PROPERY_NAME);

        if (!Strings.isNullOrEmpty(configDirPath)) {
            if (isConfigDir(configDirPath)) {
                return new File(configDirPath);
            }
        }


        if (isConfigDir("conf")) {
            return new File("conf");
        }


        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("init.conf");
        Properties prop = new Properties();
        try {
            prop.load(is);
            if (isConfigDir(prop.getProperty("forgeServerConfigDir"))) {
                return new File(prop.getProperty("forgeServerConfigDir"));
            } else {
                mLogger.error("Cannot resolve config dir. Use forgeServerConfigDir system " +
                        "property or init.conf in class path to define it with " + CONFIG_DIR_SYSTEM_PROPERY_NAME);
            }
        } catch (IOException e) {
            mLogger.error("Problem loading configuration.");
            throw new RuntimeException("Unable to start.");
        }


        return null;
    }


    private boolean isConfigDir(String dir) {
        File serverConf = new File(dir, SERVER_CONFIG_FILE);
        return serverConf.exists();
    }


    private void initLog(String logbackConfigFilename, String serverNameSuffix) {
        if (logbackConfigFilename != null) {


                LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
                JoranConfigurator jc = new JoranConfigurator();
                jc.setContext(context);
                context.reset();

                if (serverNameSuffix != null) {
                    context.putProperty("application-name", LOG_FILE_PREFIX + serverNameSuffix);
                } else {
                    context.putProperty("application-name", LOG_FILE_PREFIX);
                }

                File f = new File(mConfigDir, logbackConfigFilename);

                String logbackConfigFilePath = f.getAbsolutePath();
                try {
                    jc.doConfigure(logbackConfigFilePath);
                } catch (JoranException e) {
                    e.printStackTrace();
                }


        } else {
            mLogger.error("Cannot load the configuration! Exiting.");
            throw new RuntimeException("Unable to start.");
        }
    }


    @Override
    public void stop() {
        Spark.stop();
    }


    @Override
    public ServerConfiguration getServerConfiguration() {
        return mServerConfiguration;
    }


    private class ShutdownHookThread extends Thread {

        /**
         * <p>Logs the server shutdown.</p>
         */
        @Override
        public void run() {
            mLogger.info("Server halted");
        }
    }


    @Override
    public File getConfigDirectory() {
        return mConfigDir;
    }
}
