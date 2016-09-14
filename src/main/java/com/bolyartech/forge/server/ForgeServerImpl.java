package com.bolyartech.forge.server;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.bolyartech.forge.server.config.ServerConfiguration;
import com.bolyartech.forge.server.config.ServerConfigurationImpl;
import com.bolyartech.forge.server.module.ForgeModule;
import com.bolyartech.forge.server.module.ModuleRegister;
import com.bolyartech.forge.server.module.ModuleRegisterImpl;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import static spark.Spark.*;


abstract public class ForgeServerImpl implements ForgeServer {
    private static final String LOG_FILE_PREFIX = "forge_server";


    private final org.slf4j.Logger mLogger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private ServerConfiguration mServerConfiguration;

    private ModuleRegister mModuleRegister = new ModuleRegisterImpl();


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
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("server.conf");

        ServerConfiguration ret;

        Properties prop = new Properties();
        try {
            prop.load(is);
            ret = new ServerConfigurationImpl(prop.getProperty("server_name_suffix"),
                    prop.getProperty("logback_config_file"),
                    Integer.parseInt(prop.getProperty("http_port")),
                    Integer.parseInt(prop.getProperty("session_timeout")),
                    prop.getProperty("keystore_path"),
                    prop.getProperty("keystore_password")
            );
        } catch (IOException e) {
            mLogger.error("Problem loading configuration.");
            throw new RuntimeException("Unable to start.");
        }

        return ret;
    }


    private void initLog(String logbackConfigFilename, String serverNameSuffix) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        if (logbackConfigFilename != null) {
            URL url = classloader.getResource(logbackConfigFilename);
            if (url != null) {
                String logbackConfigFilePath;
                try {
                    logbackConfigFilePath = new File(url.toURI()).getAbsolutePath();
                } catch (URISyntaxException e) {
                    mLogger.error("Cannot get logbackConfigFilePath: {}", e);
                    throw new RuntimeException("Unable to start.");
                }

                LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
                JoranConfigurator jc = new JoranConfigurator();
                jc.setContext(context);
                context.reset();
                if (serverNameSuffix != null) {
                    context.putProperty("application-name", LOG_FILE_PREFIX + serverNameSuffix);
                } else {
                    context.putProperty("application-name", LOG_FILE_PREFIX);
                }

                try {
                    jc.doConfigure(logbackConfigFilePath);
                } catch (JoranException e) {
                    e.printStackTrace();
                }
            } else {
                mLogger.error("Logback configuration file found! Exiting.");
                throw new RuntimeException("Unable to start.");
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

}
