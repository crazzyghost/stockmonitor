package com.crazzyghost.stockmonitor.data

import java.io.IOException
import java.net.URL

object NasdaqDownloader {

    fun download(callback: DownloadCallback) {
        val link = "http://nasdaqtrader.com/dynamic/SymDir/nasdaqlisted.txt"
        try{
            val text: String = URL(link).readText()
            callback.onSuccess(text)
        }catch (e: IOException){
            callback.onFailure(e.message)
        }
    }

    interface DownloadCallback{
        fun onSuccess(result: String)
        fun onFailure(error: String?)
    }
}