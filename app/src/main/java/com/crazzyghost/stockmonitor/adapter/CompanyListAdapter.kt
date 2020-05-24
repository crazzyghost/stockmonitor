package com.crazzyghost.stockmonitor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crazzyghost.stockmonitor.R
import com.crazzyghost.stockmonitor.data.models.Company
import kotlinx.android.synthetic.main.content_company_card.view.*


class CompanyListAdapter(private var companies: List<Company>): RecyclerView.Adapter<CompanyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_company_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = companies.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val company = companies[position]
        holder.itemView.companySymbolTv.text = company.symbol
        holder.itemView.companyNameTv.text = company.name
    }

    fun updateList(list: List<Company>){
        companies = list
    }

    fun get(position: Int): Company = companies[position]


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}

