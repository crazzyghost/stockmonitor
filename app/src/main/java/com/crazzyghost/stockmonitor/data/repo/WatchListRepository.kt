package com.crazzyghost.stockmonitor.data.repo

import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.data.models.Company
import com.crazzyghost.stockmonitor.data.models.WatchListItem
import com.crazzyghost.stockmonitor.data.models.WatchListItem_
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

class WatchListRepository @Inject constructor(database: DatabaseManager){

    private val box: Box<WatchListItem> = database.boxStore().boxFor()

    fun count(): Long {
      return box.count()
    }

    private fun find(company: Company) : WatchListItem? {
        return box.query()
            .equal(WatchListItem_.name, company.name)
            .and()
            .equal(WatchListItem_.symbol, company.symbol)
            .build()
            .findFirst()
    }

    fun save(company: Company, quote: QuoteResponse?) {
        val existing = find(company)
        if(existing != null) {
            existing.previousClose = quote?.previousClose
            existing.open = quote?.open
            existing.high = quote?.high
            existing.low = quote?.low
            existing.volume = quote?.volume
            existing.change = quote?.changePercent
            box.put(existing)
            return
        }
        val item =  WatchListItem(
            name = company.name,
            symbol = company.symbol,
            previousClose = quote?.previousClose,
            open = quote?.open,
            high = quote?.high,
            low = quote?.low,
            volume = quote?.volume,
            change = quote?.changePercent
        )
        box.put(item)
    }

    fun exists(company: Company): Boolean {
        return find(company) != null
    }

}