package com.crazzyghost.stockmonitor.app

import java.util.concurrent.Executor

interface ThreadPoolManager {
    fun main(): Executor
    fun diskIO(): Executor
    fun network(): Executor
}