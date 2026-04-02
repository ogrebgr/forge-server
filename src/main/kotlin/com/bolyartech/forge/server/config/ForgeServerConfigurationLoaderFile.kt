package com.bolyartech.forge.server.config

import com.bolyartech.forge.server.config.ForgeServerConfiguration.Companion.extractDirectory
import org.slf4j.LoggerFactory
import java.net.MalformedURLException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.pathString

class ForgeServerConfigurationLoaderFile(private val configDirPath: Path) : ForgeServerConfigurationLoader {
    companion object {
        const val FORGE_CONF_FILENAME = "forge.conf"

        private const val PROP_SERVER_NAMES = "server_names"
        private const val PROP_DEPLOYMENT = "deployment"
        private const val PROP_DEPLOYMENT_URL = "deployment_url"
        private const val PROP_LOG_PREFIX = "log_prefix"
        private const val PROP_STATIC_FILES_DIR = "static_files_dir"
        private const val PROP_UPLOADS_DIR = "uploads_dir"
        private const val PROP_DOWNLOADS_DIR = "downloads_dir"
        private const val PROP_ACCESS_CONTROL_ALLOW_ORIGIN = "access_control_allow_origin"
        private const val PROP_ACCESS_CONTROL_ALLOW_METHODS = "access_control_allow_methods"
        private const val PROP_ACCESS_CONTROL_ALLOW_HEADERS = "access_control_allow_headers"
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Throws(ForgeConfigurationException::class)
    override fun load(): ForgeServerConfiguration {
        val path = Path.of(configDirPath.pathString, FORGE_CONF_FILENAME)
        if (!path.exists()) {
            throw ForgeConfigurationException("Cannot find forge configuration file (${path.pathString})")
        }

        val prop = Properties()
        Files.newInputStream(path).use {
            prop.load(it)
        }

        val logPrefix = ForgeServerConfiguration.extractStringValue(prop, PROP_LOG_PREFIX)

        val staticDir = extractDirectory(prop, PROP_STATIC_FILES_DIR, false)

        val deployment = when (val deploymentRaw = ForgeServerConfiguration.extractStringValue(prop, PROP_DEPLOYMENT)) {
            ForgeServerConfiguration.Deployment.DEVELOPMENT.deploymentName -> ForgeServerConfiguration.Deployment.DEVELOPMENT
            ForgeServerConfiguration.Deployment.TESTING.deploymentName -> ForgeServerConfiguration.Deployment.TESTING
            ForgeServerConfiguration.Deployment.STAGING.deploymentName -> ForgeServerConfiguration.Deployment.STAGING
            ForgeServerConfiguration.Deployment.PRODUCTION.deploymentName -> ForgeServerConfiguration.Deployment.PRODUCTION
            else -> {
                throw ForgeConfigurationException("Invalid value for $PROP_DEPLOYMENT -> $deploymentRaw")
            }
        }

        val deploymentUrl = ForgeServerConfiguration.extractStringValueOptional(prop, PROP_DEPLOYMENT_URL)
        if (deploymentUrl == null) {
            try {
                URL(deploymentUrl)
            } catch (_: MalformedURLException) {
                throw ForgeConfigurationException("Invalid URL IN $PROP_DEPLOYMENT_URL: $deploymentUrl")
            }
        }

        val uploadsDir = prop.getProperty(PROP_UPLOADS_DIR)

        val downloadsDir = prop.getProperty(PROP_DOWNLOADS_DIR)

        val serverNamesTmp = prop.getProperty(PROP_SERVER_NAMES)

        val serverNames = if (serverNamesTmp != null && serverNamesTmp.trim().isNotEmpty()) {
            serverNamesTmp.split(",").map { it.trim().lowercase() }.filter { it.isNotEmpty() }
        } else {
            emptyList()
        }

        val accessControlAllowOrigin: String? = prop.getProperty(PROP_ACCESS_CONTROL_ALLOW_ORIGIN)
        val accessControlAllowMethods: String? = prop.getProperty(PROP_ACCESS_CONTROL_ALLOW_METHODS)
        val accessControlAllowHeaders: String? = prop.getProperty(PROP_ACCESS_CONTROL_ALLOW_HEADERS)

        return ForgeServerConfiguration(
            serverNames,
            deployment,
            deploymentUrl,
            logPrefix,
            staticDir,
            normalizePath(uploadsDir),
            normalizePath(downloadsDir),
            accessControlAllowOrigin,
            accessControlAllowMethods,
            accessControlAllowHeaders
        )
    }

    private fun normalizePath(path: String): String {
        var pathTmp = path.lowercase(Locale.getDefault())

        if (path.length > 1) {
            if (path.endsWith("/")) {
                pathTmp = path.substring(0, path.length - 1)
            }
        }

        return pathTmp
    }
}