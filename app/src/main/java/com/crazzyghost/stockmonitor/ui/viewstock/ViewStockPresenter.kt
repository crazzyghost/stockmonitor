package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.alphavantage.AlphaVantage
import com.crazzyghost.alphavantage.AlphaVantageException
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.crazzyghost.stockmonitor.annotations.ActivityScope
import com.crazzyghost.stockmonitor.app.AppExecutors
import com.crazzyghost.stockmonitor.app.ThreadPoolManager
import javax.inject.Inject

@ActivityScope
class ViewStockPresenter @Inject constructor(private val executors: ThreadPoolManager): ViewStockContract.Presenter {

    var view: ViewStockContract.View? = null

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

    private fun onTimeSeriesResponse(response: TimeSeriesResponse){
        executors.main().execute {
            view?.onTimeSeriesResult(response)
        }
    }

    private fun onQuoteResponse(response: QuoteResponse){
        executors.main().execute {
            view?.onQuoteResult(response)
        }
    }

    private fun onError(exception: AlphaVantageException){
        println(exception.message)
    }

}