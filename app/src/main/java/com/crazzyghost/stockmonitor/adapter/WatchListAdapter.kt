package com.crazzyghost.stockmonitor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crazzyghost.stockmonitor.R
import com.crazzyghost.stockmonitor.data.models.WatchListItem
import kotlinx.android.synthetic.main.activity_view_stock.*
import kotlinx.android.synthetic.main.activity_view_stock.view.*
import kotlinx.android.synthetic.main.content_watch_list_item_card.view.*
import kotlinx.android.synthetic.main.content_watch_list_item_card.view.companyNameTv
import kotlinx.android.synthetic.main.content_watch_list_item_card.view.companySymbolTv
import kotlinx.android.synthetic.main.content_watch_list_item_card.view.highTv
import kotlinx.android.synthetic.main.content_watch_list_item_card.view.percentChangeTv
import java.text.DecimalFormat


class WatchListAdapter(private var watchListItems: List<WatchListItem>): RecyclerView.Adapter<WatchListAdapter.ViewHolder>() {

    private val fmt = DecimalFormat("#,###0.00")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_watch_list_item_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = watchListItems.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val watchListItem = watchListItems[position]
        holder.itemView.companySymbolTv.text = watchListItem.symbol
        holder.itemView.companyNameTv.text = watchListItem.name
        holder.itemView.highTv.text = fmt.format(watchListItem.high)
        val percentageChangeBackground: Int = if (watchListItem.change!! > 0.0)
            R.drawable.background_positive_change else R.drawable.background_negative_change
        var cardBackground: Int = if (watchListItem.change!! >= 0.0)
            R.drawable.background_watch_list_item_green else R.drawable.background_watch_list_item_red
        cardBackground = if (watchListItem.change!! == 0.0) R.drawable.background_stock_card else cardBackground
        holder.itemView.cardLayout.setBackgroundResource(cardBackground)
        holder.itemView.percentChangeTv.setBackgroundResource(percentageChangeBackground)
        val sign : String = if (watchListItem.change!! > 0) "+" else ""
        holder.itemView.percentChangeTv.text =  sign + fmt.format(watchListItem.change!!) + "%"

    }

    fun updateList(list: List<WatchListItem>){
        watchListItems = list
    }

    fun get(position: Int): WatchListItem = watchListItems[position]


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}

