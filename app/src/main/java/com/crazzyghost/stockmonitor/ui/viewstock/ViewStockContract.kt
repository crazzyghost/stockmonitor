package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.stockmonitor.mvp.BasePresenter
import com.crazzyghost.stockmonitor.mvp.BaseView

interface ViewStockContract {

    interface Presenter: BasePresenter<View> {

    }

    interface View: BaseView {
    }
}