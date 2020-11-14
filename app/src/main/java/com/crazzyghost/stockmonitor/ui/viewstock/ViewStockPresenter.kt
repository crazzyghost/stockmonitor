package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.alphavantage.AlphaVantage
import com.crazzyghost.alphavantage.AlphaVantageException
import com.crazzyghost.alphavantage.Fetcher
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
        repository.getQuote(
            symbol,
            Fetcher.SuccessCallback { r -> onQuoteResponse(r) },
            Fetcher.FailureCallback { e -> onError(e) }
        )
    }

    override fun fetchDaily(symbol: String?) {
        repository.getDaily(
            symbol,
            Fetcher.SuccessCallback { r -> onTimeSeriesResponse(r) },
            Fetcher.FailureCallback { e -> onError(e) }
        )
    }

    override fun fetchWeekly(symbol: String?) {
        repository.getWeekly(
            symbol,
            Fetcher.SuccessCallback { r -> onTimeSeriesResponse(r) },
            Fetcher.FailureCallback { e -> onError(e) }
        )
    }

    override fun fetchMonthly(symbol: String?) {
        repository.getMonthly(
            symbol,
            Fetcher.SuccessCallback { r -> onTimeSeriesResponse(r) },
            Fetcher.FailureCallback { e -> onError(e) }
        )
    }

    override fun fetchIntraday(symbol: String?) {
        repository.getIntraday(
            symbol,
            Fetcher.SuccessCallback { r -> onTimeSeriesResponse(r) },
            Fetcher.FailureCallback { e -> onError(e) }
        )
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