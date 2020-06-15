package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.annotations.ActivityScope
import com.crazzyghost.stockmonitor.app.ThreadPoolManager
import com.crazzyghost.stockmonitor.data.AppDatabaseManager
import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.data.models.WatchListItem
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

@ActivityScope
class HomePresenter @Inject constructor(
    private var executors: ThreadPoolManager,
    private var database: DatabaseManager
) : HomeContract.Presenter {

    private var view: HomeContract.View? = null

    override fun attach(view: HomeContract.View) {
        this.view = view
    }

    override fun drop() {
        view = null
    }

    override fun getWatchListItems() {
        executors.diskIO().execute {
            val box: Box<WatchListItem> = (database as AppDatabaseManager).boxStore.boxFor()
            val list = box.all
            executors.main().execute{
                view?.onWatchListItems(list)
            }
        }
    }
}