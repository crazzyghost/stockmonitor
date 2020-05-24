package com.crazzyghost.stockmonitor.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crazzyghost.stockmonitor.R
import com.crazzyghost.stockmonitor.app.App
import com.crazzyghost.stockmonitor.ui.search.Search
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class Home : AppCompatActivity(), HomeContract.View {


    @Inject lateinit var presenter: HomePresenter
    lateinit var component: HomeComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        component = (applicationContext as App).component.homeComponent().create()
        component.inject(this)
        initUi()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.drop()
    }

    fun initUi(){
        searchBtn.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }
    }
}
