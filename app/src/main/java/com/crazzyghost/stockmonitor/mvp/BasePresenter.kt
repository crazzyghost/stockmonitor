package com.crazzyghost.stockmonitor.mvp

interface BasePresenter<T> {
    fun attach(view: T)
    fun drop()
}