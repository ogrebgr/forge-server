package com.bolyartech.forge.server


interface WebServerStopper {
    fun stop()
}

interface WebServer : WebServerStopper {
    /**
     * @return `true` if server is started successfully, `false` otherwise.
     */
    fun start(): Boolean
    fun getInstrumentation(): WebServerInstrumentationReader
}

interface WebServerInstrumentationReader {
    fun getQueueSize(): Int
    fun getReadyThreads(): Int
    fun getUtilizationRate(): Double
}
