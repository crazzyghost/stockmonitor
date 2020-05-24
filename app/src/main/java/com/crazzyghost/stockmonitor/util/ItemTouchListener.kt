package com.crazzyghost.stockmonitor.util

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class ItemTouchListener constructor(context: Context?, recycleView: RecyclerView, listener: ClickListener?): RecyclerView.OnItemTouchListener {

    private var listener: ClickListener? = null
    private var gestureDetector: GestureDetector? = null

    init {
        this.listener = listener
        gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child: View? = recycleView.findChildViewUnder(e.x, e.y)
                if (child != null && listener != null) {
                    listener.onLongClick(child, recycleView.getChildAdapterPosition(child))
                }
            }
        })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child: View? = rv.findChildViewUnder(e.x, e.y)
        if (child != null && listener != null && gestureDetector!!.onTouchEvent(e)) {
            listener!!.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) = Unit

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) = Unit
}