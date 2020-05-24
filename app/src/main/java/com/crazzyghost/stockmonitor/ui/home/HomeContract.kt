package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.mvp.BasePresenter
import com.crazzyghost.stockmonitor.mvp.BaseView

interface HomeContract {

    interface Presenter : BasePresenter<View>{

    }

    interface View: BaseView{

    }
}