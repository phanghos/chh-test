package com.android.taitasciore.chhtest.util

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.taitasciore.chhtest.BaseApplication
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment extensions
 */
fun Fragment.getBaseApplication(): BaseApplication? {
    return activity?.application as BaseApplication?
}

fun Fragment.setTitle(title: String) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = title
}

fun Fragment.showView(view: View) {
    view.visibility = View.VISIBLE
}

fun Fragment.hideView(view: View) {
    view.visibility = View.GONE
}

/**
 * RecyclerView extensions
 */
fun RecyclerView.addDivider(context: Context?, resId: Int) {
    val divider = ContextCompat.getDrawable(context!!, resId)
    val itemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
    divider?.let {
        itemDecoration.setDrawable(divider)
        this.addItemDecoration(itemDecoration)
    }
}

/**
 * String extensions
 */
fun String.parseSimpleDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    return dateFormat.parse(this)
}

/**
 * Date extensions
 */
fun Date.formatFullDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return dateFormat.format(this)
}