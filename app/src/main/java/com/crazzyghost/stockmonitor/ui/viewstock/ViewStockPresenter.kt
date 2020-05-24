package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.stockmonitor.annotations.ActivityScope
import javax.inject.Inject

@ActivityScope
class ViewStockPresenter @Inject constructor(): ViewStockContract.Presenter {

    var view: ViewStockContract.View? = null

    override fun attach(view: ViewStockContract.View) {
        this.view = view
    }

    override fun drop() {
        this.view = null
    }
}