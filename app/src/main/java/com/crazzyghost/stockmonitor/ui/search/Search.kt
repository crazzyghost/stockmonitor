package com.crazzyghost.stockmonitor.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crazzyghost.stockmonitor.R
import com.crazzyghost.stockmonitor.adapter.CompanyListAdapter
import com.crazzyghost.stockmonitor.app.App
import com.crazzyghost.stockmonitor.data.models.Company
import com.crazzyghost.stockmonitor.ui.viewstock.ViewStock
import com.crazzyghost.stockmonitor.util.ClickListener
import com.crazzyghost.stockmonitor.util.Constants
import com.crazzyghost.stockmonitor.util.ItemTouchListener
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class Search : AppCompatActivity(), SearchContract.View, CoroutineScope {

    @Inject
    lateinit var presenter: SearchContract.Presenter
    lateinit var component: SearchComponent
    lateinit var viewAdapter: CompanyListAdapter
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAnimator: RecyclerView.ItemAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        component = (applicationContext as App).component.searchComponent().create()
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

    private fun initUi(){
        viewAdapter = CompanyListAdapter(presenter.getCompanies())
        viewManager = LinearLayoutManager(this, RecyclerView.VERTICAL ,false)
        viewAnimator = DefaultItemAnimator()
        companyListRv.apply {
            adapter = viewAdapter
            itemAnimator = viewAnimator
            layoutManager = viewManager
            setHasFixedSize(true)
            scrollToPosition(viewAdapter.itemCount - 1)
        }

        companyListRv.addOnItemTouchListener(ItemTouchListener(this, companyListRv, object: ClickListener {
            override fun onClick(view: View?, position: Int) {
                val company = viewAdapter.get(position)
                val intent = Intent(this@Search, ViewStock::class.java)
                intent.putExtra(Constants.EXTRA_STOCK_NAME, company.name)
                intent.putExtra(Constants.EXTRA_STOCK_SYMBOL, company.symbol)
                startActivity(intent)
            }
            override fun onLongClick(view: View?, position: Int) = Unit
        }))

        dateTv.text = SimpleDateFormat(Constants.FORMAT_SEARCH_DATE, Locale.UK).format(Date())

        val watcher = object : TextWatcher {
            private var searchFor = ""
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                if(searchText == "") {
                    onSearchResult(listOf())
                    return
                }
                if(searchText == searchFor) return
                searchFor = searchText
                launch {
                    delay(300)
                    if(searchText != searchFor) return@launch
                    presenter.search(s.toString())
                }
            }
            override fun afterTextChanged(s: Editable?) =  Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        }
        searchEt.addTextChangedListener(watcher)
        backBtn.setOnClickListener { finish() }
    }

    override fun onSearchResult(list: List<Company>) {
        viewAdapter.updateList(list)
        viewAdapter.notifyDataSetChanged()
        companyListRv.scheduleLayoutAnimation()
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main
}
