package com.crazzyghost.stockmonitor.ui.viewstock

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.StockUnit
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.crazzyghost.stockmonitor.R
import com.crazzyghost.stockmonitor.app.App
import com.crazzyghost.stockmonitor.util.Constants.EXTRA_STOCK_NAME
import com.crazzyghost.stockmonitor.util.Constants.EXTRA_STOCK_SYMBOL
import kotlinx.android.synthetic.main.activity_view_stock.*
import java.text.DecimalFormat
import javax.inject.Inject

class ViewStock : AppCompatActivity(), ViewStockContract.View {

    @Inject
    lateinit var presenter: ViewStockContract.Presenter
    lateinit var component: ViewStockComponent
    var name: String? = ""
    var symbol: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stock)
        component = (applicationContext as App).component.viewStockComponent().create()
        component.inject(this)
        name = intent.getStringExtra(EXTRA_STOCK_NAME)
        symbol = intent.getStringExtra(EXTRA_STOCK_SYMBOL)
        initUi()
    }

    private fun initUi(){
        presenter.fetchDaily(symbol)
        presenter.fetchQuote(symbol)
        companyNameTv.text = name
        companySymbolTv.text = symbol
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.drop()
    }

    override fun onQuoteResult(response: QuoteResponse) {
        val fmt = DecimalFormat("#,###.00")
        previousCloseTv.text = fmt.format(response.previousClose)
        openTv.text = fmt.format(response.open)
        highTv.text = fmt.format(response.high)
        lowTv.text = fmt.format(response.low)
        volumeTv.text = fmt.format(response.volume)
        bannerHighTv.text = fmt.format(response.high)
        percentChangeTv.text =  if (response.changePercent > 0) "+" else "" + fmt.format(response.changePercent) + "%"

        val resource: Int = if (response.changePercent > 0)
            R.drawable.background_positive_change else R.drawable.background_negative_change

        percentChangeTv.setBackgroundResource(resource)

    }

    override fun onTimeSeriesResult(response: TimeSeriesResponse) {
        progressBar.visibility = GONE
        response.stockUnits.forEach(::println)
    }
}
