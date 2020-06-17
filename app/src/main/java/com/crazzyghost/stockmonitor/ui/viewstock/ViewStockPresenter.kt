package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.alphavantage.AlphaVantage
import com.crazzyghost.alphavantage.AlphaVantageException
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.crazzyghost.stockmonitor.annotations.ActivityScope
import com.crazzyghost.stockmonitor.app.AppExecutors
import com.crazzyghost.stockmonitor.app.ThreadPoolManager
import com.crazzyghost.stockmonitor.data.AppDatabaseManager
import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.data.models.Company
import com.crazzyghost.stockmonitor.data.models.WatchListItem
import com.crazzyghost.stockmonitor.data.models.WatchListItem_
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

@ActivityScope
class ViewStockPresenter @Inject constructor(
    private val executors: ThreadPoolManager,
    private var database: DatabaseManager
): ViewStockContract.Presenter {

    var view: ViewStockContract.View? = null
    private var quote: QuoteResponse? = null

    override fun attach(view: ViewStockContract.View) {
        this.view = view
    }

    override fun drop() {
        this.view = null
    }

    override fun fetchQuote(symbol: String?) {
        AlphaVantage.api()
            .timeSeries()
            .quote()
            .forSymbol(symbol)
            .onSuccess { r -> onQuoteResponse(r as QuoteResponse)}
            .onFailure { e -> onError(e) }
            .fetch()
    }

    override fun fetchDaily(symbol: String?) {
        AlphaVantage.api()
            .timeSeries()
            .daily()
            .forSymbol(symbol)
            .onSuccess { r -> onTimeSeriesResponse(r as TimeSeriesResponse)}
            .onFailure { e -> onError(e) }
            .fetch()
    }

    override fun fetchWeekly(symbol: String?) {
        AlphaVantage.api()
            .timeSeries()
            .weekly()
            .forSymbol(symbol)
            .onSuccess { r -> onTimeSeriesResponse(r as TimeSeriesResponse)}
            .onFailure { e -> onError(e) }
            .fetch()
    }

    override fun fetchMonthly(symbol: String?) {
        AlphaVantage.api()
            .timeSeries()
            .monthly()
            .forSymbol(symbol)
            .onSuccess { r -> onTimeSeriesResponse(r as TimeSeriesResponse)}
            .onFailure { e -> onError(e) }
            .fetch()
    }

    override fun fetchIntraday(symbol: String?) {
        AlphaVantage.api()
            .timeSeries()
            .intraday()
            .forSymbol(symbol)
            .onSuccess { r -> onTimeSeriesResponse(r as TimeSeriesResponse)}
            .onFailure { e -> onError(e) }
            .fetch()
    }

    private fun itemInWatchList(company: Company) : Boolean {
        val box: Box<WatchListItem> = (database as AppDatabaseManager).boxStore.boxFor()
        return box.query()
            .equal(WatchListItem_.name, company.name)
            .and()
            .equal(WatchListItem_.symbol, company.symbol)
            .build()
            .find().size > 0
    }

    override fun addToWatchList(company: Company){
        executors.diskIO().execute {
            try {

                val box: Box<WatchListItem> = (database as AppDatabaseManager).boxStore.boxFor()
                if (box.count() == 5L) {
                    executors.main().execute {
                        view?.onWatchListItemsExceeded()
                    }
                    return@execute
                }
                if(itemInWatchList(company)){
                    executors.main().execute {
                        view?.onItemInWatchList()
                    }
                    return@execute
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
                executors.main().execute{
                    view?.onItemAddedToWatchList(true)
                }
            }catch (e: Exception){
                executors.main().execute{
                    view?.onItemAddedToWatchList(false)
                }
            }

        }
    }

    private fun onTimeSeriesResponse(response: TimeSeriesResponse){
        executors.main().execute {
            view?.onTimeSeriesResult(response)
        }
    }

    private fun onQuoteResponse(response: QuoteResponse){
        quote = response
        executors.main().execute {
            view?.onQuoteResult(response)
        }
    }

    private fun onError(exception: AlphaVantageException){
        println(exception.message)
    }

    override fun updateIfInWatchList(company: Company){
        if(itemInWatchList(company)){
            executors.diskIO().execute {
                val box: Box<WatchListItem> = (database as AppDatabaseManager).boxStore.boxFor()
                val item = box.query()
                    .equal(WatchListItem_.name, company.name)
                    .and()
                    .equal(WatchListItem_.symbol, company.symbol)
                    .build()
                    .findFirst()

                item!!.previousClose = quote?.previousClose
                item.open = quote?.open
                item.high = quote?.high
                item.low = quote?.low
                item.volume = quote?.volume
                item.change = quote?.changePercent
                box.put(item)
            }

        }
    }

}