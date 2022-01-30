package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.data.models.WatchListItem
import com.crazzyghost.stockmonitor.mvp.BasePresenter
import com.crazzyghost.stockmonitor.mvp.BaseView

interface HomeContract {

    interface Presenter : BasePresenter<View> {

        fun getWatchListItems()
        fun deleteItem(item: WatchListItem, adapterPosition: Int)
    }

    interface View: BaseView{
        fun onWatchListItems(items: List<WatchListItem>)
        fun onItemDeleted(adapterPosition: Int)
    }
}