package com.crazzyghost.stockmonitor.app

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


class AppExecutors: ThreadPoolManager {

    private var main: Executor
    private var diskIO: Executor
    private var network: Executor

    constructor(main: Executor, diskIO: Executor, network: Executor){
        this.main = main
        this.diskIO = diskIO
        this.network = network
    }

    @Inject
    constructor(): this(MainThreadExecutor(), Executors.newSingleThreadExecutor(), Executors.newScheduledThreadPool(3))

    override fun main() = main

    override fun diskIO() = diskIO

    override fun network() = network

    class MainThreadExecutor : Executor {
        private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}