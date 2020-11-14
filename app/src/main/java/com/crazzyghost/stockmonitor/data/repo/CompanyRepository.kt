package com.crazzyghost.stockmonitor.data.repo

import com.crazzyghost.alphavantage.AlphaVantage
import com.crazzyghost.alphavantage.Fetcher
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.data.models.Company
import com.crazzyghost.stockmonitor.data.models.Company_
import com.crazzyghost.stockmonitor.data.models.WatchListItem
import com.crazzyghost.stockmonitor.data.models.WatchListItem_
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

class CompanyRepository @Inject constructor(database: DatabaseManager){

    private val box: Box<Company> = database.boxStore().boxFor()

    fun search(query: String): List<Company> {
        return box.query()
            .startsWith(Company_.name, query)
            .or()
            .startsWith(Company_.symbol, query)
            .build()
            .find()
            .take(10)
    }

}