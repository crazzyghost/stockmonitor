package com.crazzyghost.stockmonitor.data

import io.objectbox.BoxStore

interface DatabaseManager {

    fun setupCompanyList()
    fun boxStore(): BoxStore
}