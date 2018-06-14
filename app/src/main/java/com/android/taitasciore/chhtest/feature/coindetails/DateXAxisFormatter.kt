package com.android.taitasciore.chhtest.feature.coindetails

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class DateXAxisFormatter : IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        val timestamp = (value * 100000).toLong()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(Date(timestamp))
    }
}