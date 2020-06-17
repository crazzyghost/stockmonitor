package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.StockUnit
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.crazzyghost.stockmonitor.data.models.Company
import com.crazzyghost.stockmonitor.mvp.BasePresenter
import com.crazzyghost.stockmonitor.mvp.BaseView

interface ViewStockContract {

    interface Presenter: BasePresenter<View> {
        fun fetchIntraday(symbol: String?)
        fun fetchMonthly(symbol: String?)
        fun fetchWeekly(symbol: String?)
        fun fetchDaily(symbol: String?)
        fun fetchQuote(symbol: String?)
        fun addToWatchList(company: Company)
        fun updateIfInWatchList(company: Company)
    }

    interface View: BaseView {
        fun onQuoteResult(response: QuoteResponse)
        fun onTimeSeriesResult(response: TimeSeriesResponse)
        fun onItemAddedToWatchList(status: Boolean)
        fun onWatchListItemsExceeded()
        fun onItemInWatchList()
    }
}