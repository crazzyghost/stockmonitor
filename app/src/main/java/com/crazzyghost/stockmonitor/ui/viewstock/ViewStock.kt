package com.crazzyghost.stockmonitor.ui.viewstock

import android.R.attr.entries
import android.graphics.Color
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.crazzyghost.stockmonitor.R
import com.crazzyghost.stockmonitor.app.App
import com.crazzyghost.stockmonitor.util.Constants.EXTRA_STOCK_NAME
import com.crazzyghost.stockmonitor.util.Constants.EXTRA_STOCK_SYMBOL
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
        graph.setNoDataText("")
        graph.axisRight.isEnabled = false
        graph.axisLeft.isEnabled = true
        graph.axisLeft.axisLineColor = Color.parseColor("#FFFFFFFF")
        graph.axisLeft.setDrawAxisLine(false)
        graph.axisLeft.setDrawTopYLabelEntry(true)
        graph.axisLeft.setDrawLabels(false)
        graph.xAxis.isEnabled = false
        graph.legend.isEnabled = false
        graph.description.isEnabled = false
        graph.fitScreen()
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
        if(response.errorMessage == null){
            val fmt = DecimalFormat("#,###0.00")
            previousCloseTv.text = fmt.format(response.previousClose)
            openTv.text = fmt.format(response.open)
            highTv.text = fmt.format(response.high)
            lowTv.text = fmt.format(response.low)
            volumeTv.text = fmt.format(response.volume)
            bannerHighTv.text = fmt.format(response.high)
            val sign : String = if (response.changePercent > 0) "+" else ""
            percentChangeTv.text =  sign + fmt.format(response.changePercent) + "%"

            val resource: Int = if (response.changePercent > 0)
                R.drawable.background_positive_change else R.drawable.background_negative_change

            percentChangeTv.setBackgroundResource(resource)
        }else{
            Toast.makeText(this, response.errorMessage, Toast.LENGTH_LONG).show()
        }

    }

    override fun onTimeSeriesResult(response: TimeSeriesResponse) {
        progressBar.visibility = GONE
        if(response.errorMessage == null){

            val entries = mutableListOf<Entry>()
            for(i in 0 until response.stockUnits.size){
                entries.add(Entry(i.toFloat(), response.stockUnits[i].high.toFloat()))
            }
            val dataSet = LineDataSet(entries, "")
            dataSet.lineWidth = 1f
            dataSet.setDrawCircles(false)
            dataSet.resetColors()
            dataSet.color = Color.parseColor("#FFFFFFFF")
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            val data = LineData(dataSet)
            graph.data = data
            graph.invalidate()
        }else{
            Toast.makeText(this, response.errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
