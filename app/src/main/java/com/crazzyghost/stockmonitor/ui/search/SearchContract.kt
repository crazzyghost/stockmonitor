package com.crazzyghost.stockmonitor.ui.search

import com.crazzyghost.stockmonitor.data.models.Company
import com.crazzyghost.stockmonitor.mvp.BasePresenter
import com.crazzyghost.stockmonitor.mvp.BaseView

interface SearchContract {

    interface Presenter: BasePresenter<View> {
        fun getCompanies() : List<Company>
        fun search(filter: String)
    }

    interface View: BaseView {
        fun onSearchResult(list: List<Company>)
    }
}