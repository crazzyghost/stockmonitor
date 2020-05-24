package com.crazzyghost.stockmonitor.ui.search

import android.util.Log
import com.crazzyghost.stockmonitor.annotations.ActivityScope
import com.crazzyghost.stockmonitor.data.AppDatabaseManager
import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.data.models.Company
import com.crazzyghost.stockmonitor.data.models.Company_
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import javax.inject.Inject

@ActivityScope
class SearchPresenter @Inject constructor(private var database: DatabaseManager): SearchContract.Presenter {

    var view: SearchContract.View? = null
    private val companies = ArrayList<Company>()
    private var searchTerm = ""
    override fun getCompanies() = companies

    override fun search(filter: String) {
        searchTerm = filter
        Log.e("SearchPresenter", searchTerm)
        val companyBox: Box<Company> = (database as AppDatabaseManager).boxStore.boxFor()
        val list = companyBox.query()
            .startsWith(Company_.name, filter)
            .or()
            .startsWith(Company_.symbol, filter)
            .build()
            .find()
            .take(10)
        if (searchTerm == "") {
            view?.onSearchResult(listOf())
            return
        }
        view?.onSearchResult(list)
    }



    override fun attach(view: SearchContract.View) {
        this.view = view
    }

    override fun drop() {
        this.view = null
    }
}