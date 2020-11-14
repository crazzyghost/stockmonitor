package com.crazzyghost.stockmonitor.data.repo

import com.crazzyghost.alphavantage.AlphaVantage
import com.crazzyghost.alphavantage.Fetcher
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
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

    fun all() : List<WatchListItem> {
        return box.all
    }

    fun delete(item: WatchListItem) {
        box.query()
            .equal(WatchListItem_.name, item.name)
            .and()
            .equal(WatchListItem_.symbol, item.symbol)
            .build()
            .remove()
    }

    fun getQuote(
        symbol: String?,
        onSuccessCallback: Fetcher.SuccessCallback<QuoteResponse>,
        onFailureCallback: Fetcher.FailureCallback
    ) {

        AlphaVantage.api()
            .timeSeries()
            .quote()
            .forSymbol(symbol)
            .onSuccess(onSuccessCallback)
            .onFailure(onFailureCallback)
            .fetch()
    }

    fun getDaily(
        symbol: String?,
        onSuccessCallback: Fetcher.SuccessCallback<TimeSeriesResponse>,
        onFailureCallback: Fetcher.FailureCallback
    ) {

        AlphaVantage.api()
            .timeSeries()
            .daily()
            .forSymbol(symbol)
            .onSuccess(onSuccessCallback)
            .onFailure(onFailureCallback)
            .fetch()
    }

    fun getWeekly(
        symbol: String?,
        onSuccessCallback: Fetcher.SuccessCallback<TimeSeriesResponse>,
        onFailureCallback: Fetcher.FailureCallback
    ) {

        AlphaVantage.api()
            .timeSeries()
            .weekly()
            .forSymbol(symbol)
            .onSuccess(onSuccessCallback)
            .onFailure(onFailureCallback)
            .fetch()
    }

    fun getMonthly(
        symbol: String?,
        onSuccessCallback: Fetcher.SuccessCallback<TimeSeriesResponse>,
        onFailureCallback: Fetcher.FailureCallback
    ) {

        AlphaVantage.api()
            .timeSeries()
            .monthly()
            .forSymbol(symbol)
            .onSuccess(onSuccessCallback)
            .onFailure(onFailureCallback)
            .fetch()
    }


    fun getIntraday(
        symbol: String?,
        onSuccessCallback: Fetcher.SuccessCallback<TimeSeriesResponse>,
        onFailureCallback: Fetcher.FailureCallback
    ) {

        AlphaVantage.api()
            .timeSeries()
            .intraday()
            .forSymbol(symbol)
            .onSuccess(onSuccessCallback)
            .onFailure(onFailureCallback)
            .fetch()
    }
}