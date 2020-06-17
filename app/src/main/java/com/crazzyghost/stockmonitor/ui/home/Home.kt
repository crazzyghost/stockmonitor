package com.crazzyghost.stockmonitor.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crazzyghost.stockmonitor.R
import com.crazzyghost.stockmonitor.adapter.WatchListAdapter
import com.crazzyghost.stockmonitor.app.App
import com.crazzyghost.stockmonitor.data.models.WatchListItem
import com.crazzyghost.stockmonitor.ui.search.Search
import com.crazzyghost.stockmonitor.ui.viewstock.ViewStock
import com.crazzyghost.stockmonitor.util.ClickListener
import com.crazzyghost.stockmonitor.util.Constants
import com.crazzyghost.stockmonitor.util.ItemTouchListener
import com.crazzyghost.stockmonitor.util.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class Home : AppCompatActivity(), HomeContract.View {


    @Inject lateinit var presenter: HomePresenter
    lateinit var component: HomeComponent
    lateinit var viewAdapter: WatchListAdapter
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAnimator: RecyclerView.ItemAnimator

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
        presenter.getWatchListItems()
    }

    override fun onPause() {
        super.onPause()
        presenter.drop()
        println("onpause")
    }

    private fun initUi(){
        searchBtn.setOnClickListener {
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

        presenter.getWatchListItems()
        viewAdapter = WatchListAdapter(listOf())
        viewManager = LinearLayoutManager(this, RecyclerView.VERTICAL ,false)
        viewAnimator = DefaultItemAnimator()
        watchListRv.apply {
            adapter = viewAdapter
            itemAnimator = viewAnimator
            layoutManager = viewManager
            setHasFixedSize(true)
            scrollToPosition(viewAdapter.itemCount - 1)
        }

        watchListRv.addOnItemTouchListener(ItemTouchListener(this, watchListRv, object: ClickListener {
            override fun onClick(view: View?, position: Int) {
                val item = viewAdapter.get(position)
                val intent = Intent(this@Home, ViewStock::class.java)
                intent.putExtra(Constants.EXTRA_STOCK_NAME, item.name)
                intent.putExtra(Constants.EXTRA_STOCK_SYMBOL, item.symbol)
                startActivity(intent)
            }
            override fun onLongClick(view: View?, position: Int) = Unit
        }))

        val handler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = viewAdapter.get(viewHolder.adapterPosition)
                presenter.deleteItem(item, viewHolder.adapterPosition)
                return
            }
        }
        ItemTouchHelper(handler).attachToRecyclerView(watchListRv)
    }

    override fun onWatchListItems(items: List<WatchListItem>){
        if(items.isNotEmpty()){
            items.forEach(::println)
            watchListRv.visibility = View.VISIBLE
            watchListTv.visibility = View.VISIBLE
            emptyWatchlistTv.visibility = View.GONE
            viewAdapter.updateList(items)
            viewAdapter.notifyDataSetChanged()
        }else{
            emptyWatchlistTv.visibility = View.VISIBLE
        }
    }

    override fun onItemDeleted(adapterPosition: Int) {
        viewAdapter.delete(adapterPosition)
        viewAdapter.notifyItemRemoved(adapterPosition)
        if(viewAdapter.itemCount == 0) {
            emptyWatchlistTv.visibility = View.VISIBLE
            watchListTv.visibility = View.GONE
            watchListRv.visibility = View.GONE
        }
    }
}
