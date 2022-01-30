package com.crazzyghost.stockmonitor.mvp

interface BasePresenter<in View> {
    fun attach(view: View)
    fun drop()
}