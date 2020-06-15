package com.crazzyghost.stockmonitor.data

import com.crazzyghost.stockmonitor.data.models.Company

interface DatabaseManager {

    fun setupCompanyList()
    fun addToWatchList(company: Company)
    fun removeFromWatchList(company: Company)

}