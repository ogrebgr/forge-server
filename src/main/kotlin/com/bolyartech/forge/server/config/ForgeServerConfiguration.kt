package com.bolyartech.forge.server.config

import java.io.File
import java.util.*

data class ForgeServerConfiguration(
    val serverNames: List<String>,
    val deployment: Deployment,
    val deploymentUrl: String?,
    val logPrefix: String,
    val staticFilesDir: String,
    val uploadsDirectory: String,
    val downloadsDirectory: String,

    val accessControlAllowOrigin: String?,
    val accessControlAllowMethods: String?,
    val accessControlAllowHeaders: String?
) {
    enum class Deployment(val deploymentName: String) {
        DEVELOPMENT("development"),
        TESTING("testing"),
        STAGING("staging"),
        PRODUCTION("production");
    }

    companion object {
        const val DEFAULT_IS_PATH_INFO_ENABLED = true
        const val DEFAULT_MAX_SLASHES_IN_PATH_INFO = 10

        fun extractIntValue(prop: Properties, propertyName: String, default: Int? = null): Int {
            val tmp = prop.getProperty(propertyName) ?: run {
                if (default != null) {
                    return default
                } else {
                    throw ForgeConfigurationException("$propertyName is missing/empty")
                }
            }

            return try {
                tmp.toInt()
            } catch (e: NumberFormatException) {
                throw ForgeConfigurationException("$propertyName is not integer")
            }
        }

        fun extractIntValuePositive(prop: Properties, propertyName: String, default: Int? = null): Int {
            val tmp = extractIntValue(prop, propertyName, default)
            if (tmp <= 0) {
                throw ForgeConfigurationException("$propertyName is not positive integer")
            }

            return tmp
        }

        fun extractIntValue0Positive(prop: Properties, propertyName: String, default: Int? = null): Int {
            val tmp = extractIntValue(prop, propertyName, default)
            if (tmp < 0) {
                throw ForgeConfigurationException("$propertyName is not positive integer or 0")
            }

            return tmp
        }

        fun extractStringValue(prop: Properties, propertyName: String): String {
            val tmp = prop.getProperty(propertyName) ?: throw ForgeConfigurationException("$propertyName is missing/empty")

            return tmp
        }

        fun extractStringValueOptional(prop: Properties, propertyName: String): String? {
            return prop.getProperty(propertyName)
        }

        fun extractBooleanValue(prop: Properties, propertyName: String): Boolean {
            val tmp = prop.getProperty(propertyName) ?: throw ForgeConfigurationException("$propertyName is missing/empty")

            return tmp.toBoolean()
        }

        fun extractHumanBoolean(prop: Properties, key: String): Boolean {
            val str = extractStringValue(prop, key)
            val tmp = str.trim().lowercase()
            return tmp == "1" || tmp.lowercase(Locale.getDefault()) == "yes" ||
                    tmp.lowercase(Locale.getDefault()) == "y" || tmp.lowercase(Locale.getDefault()) == "true"
        }

        fun extractDirectory(prop: Properties, key: String, checkWritable: Boolean = false): String {
            val dir = prop.getProperty(key)
            if (dir.isNullOrEmpty()) {
                throw ForgeConfigurationException("$key is not set in the configuration file")
            }

            val tmp = File(dir)
            if (!tmp.exists()) {
                throw ForgeConfigurationException("$key dir $dir does not exist")
            }

            if (!tmp.isDirectory) {
                throw ForgeConfigurationException("$key dir $dir is not directory")
            }

            if (checkWritable && !tmp.canWrite()) {
                throw ForgeConfigurationException("$key dir $dir is not writable")
            }

            return dir
        }
    }

    init {
        if (logPrefix.isEmpty()) {
            throw IllegalArgumentException("serverLogName cannot be empty")
        }

        if (staticFilesDir.isEmpty()) {
            throw IllegalArgumentException("staticFilesDir cannot be empty")
        }

        if (staticFilesDir.endsWith(File.separator)) {
            throw IllegalArgumentException("staticFilesDir must NOT end with ${File.separator}")
        }
    }
}