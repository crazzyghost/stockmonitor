package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.di.ActivityScope
import com.crazzyghost.stockmonitor.data.models.WatchListItem
import com.crazzyghost.stockmonitor.data.repo.WatchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class HomePresenter @Inject constructor(
    private var repository: WatchListRepository
) : HomeContract.Presenter {

    private var view: HomeContract.View? = null

    override fun attach(view: HomeContract.View) {
        this.view = view
    }

    override fun drop() {
        view = null
    }

    override fun getWatchListItems() {
        GlobalScope.launch(Dispatchers.IO) {
            val list = repository.all()
            launch(Dispatchers.Main){
                view?.onWatchListItems(list)
            }
        }
    }

    override fun deleteItem(item: WatchListItem, adapterPosition: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.delete(item)
            launch(Dispatchers.Main) {
                view?.onItemDeleted(adapterPosition)
            }
        }
    }

}