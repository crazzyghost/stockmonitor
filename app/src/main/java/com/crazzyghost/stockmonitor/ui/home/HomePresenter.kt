package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.annotations.ActivityScope
import javax.inject.Inject

@ActivityScope
class HomePresenter @Inject constructor() : HomeContract.Presenter {

    private var view: HomeContract.View? = null

    override fun attach(view: HomeContract.View) {
        this.view = view
    }

    override fun drop() {
        view = null
    }
}