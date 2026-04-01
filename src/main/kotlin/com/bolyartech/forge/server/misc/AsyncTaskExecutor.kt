package com.bolyartech.forge.server.misc

interface AsyncTaskExecutor {
    fun execute(task: AsyncTask, ttlMillis: Int = 5000): AsyncTaskData
    fun ack(id: Int, token: String)
    fun acknowledge(id: Int, token: String)
}

