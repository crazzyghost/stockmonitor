package com.crazzyghost.stockmonitor.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseMvpActivity<in View>: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        getViewPresenter().attach(view = this as View)
    }

    override fun onPause() {
        super.onPause()
        getViewPresenter().drop()
    }

    abstract fun getViewPresenter(): BasePresenter<View>
}