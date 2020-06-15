package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.data.models.WatchListItem
import com.crazzyghost.stockmonitor.mvp.BasePresenter
import com.crazzyghost.stockmonitor.mvp.BaseView

interface HomeContract {

    interface Presenter : BasePresenter<View>{

        fun getWatchListItems()
    }

    interface View: BaseView{
        fun onWatchListItems(items: List<WatchListItem>)
    }
}