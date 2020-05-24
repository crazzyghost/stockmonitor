package com.crazzyghost.stockmonitor.ui.viewstock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crazzyghost.stockmonitor.R
import com.crazzyghost.stockmonitor.app.App
import javax.inject.Inject

class ViewStock : AppCompatActivity(), ViewStockContract.View {

    @Inject
    lateinit var presenter: ViewStockContract.Presenter
    lateinit var component: ViewStockComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stock)
        component = (applicationContext as App).component.viewStockComponent().create()
        component.inject(this)
        initUi()
    }

    private fun initUi(){

    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.drop()
    }

}
