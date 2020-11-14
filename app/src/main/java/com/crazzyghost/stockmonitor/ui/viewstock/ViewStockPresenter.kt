package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.alphavantage.AlphaVantage
import com.crazzyghost.alphavantage.AlphaVantageException
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.crazzyghost.stockmonitor.annotations.ActivityScope
import com.crazzyghost.stockmonitor.data.models.Company
import com.crazzyghost.stockmonitor.data.repo.WatchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class ViewStockPresenter @Inject constructor(
    private var repository: WatchListRepository
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


    override fun addToWatchList(company: Company){

        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (repository.count() == 5L) {
                    launch(Dispatchers.Main) {
                        view?.onWatchListItemsExceeded()
                    }
                    return@launch
                }
                if (repository.exists(company)) {
                    launch(Dispatchers.Main) {
                        view?.onItemInWatchList()
                    }
                    return@launch
                }
                repository.save(company, quote)
                launch(Dispatchers.Main){
                    view?.onItemAddedToWatchList(true)
                }

            } catch(e: Exception) {
                launch(Dispatchers.Main){
                    view?.onItemAddedToWatchList(false)
                }
            }
        }

    }

    private fun onTimeSeriesResponse(response: TimeSeriesResponse){
        GlobalScope.launch(Dispatchers.Main){
            view?.onTimeSeriesResult(response)
        }
    }

    private fun onQuoteResponse(response: QuoteResponse){
        quote = response
        GlobalScope.launch(Dispatchers.Main){
            view?.onQuoteResult(response)
        }
    }

    private fun onError(exception: AlphaVantageException){
        println(exception.message)
    }

    override fun updateIfInWatchList(company: Company){
        GlobalScope.launch(Dispatchers.IO) {
            if(repository.exists(company)){
                repository.save(company, quote)
            }
        }
    }

}