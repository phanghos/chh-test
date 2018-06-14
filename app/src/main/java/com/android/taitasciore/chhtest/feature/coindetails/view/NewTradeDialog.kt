package com.android.taitasciore.chhtest.feature.coindetails.view

import android.app.Dialog
import android.arch.lifecycle.LiveData
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.widget.EditText
import com.android.taitasciore.chhtest.R
import com.android.taitasciore.chhtest.data.entity.TradeEntity
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.util.SingleLiveEvent
import com.android.taitasciore.chhtest.util.formatFullDate
import java.util.*

class NewTradeDialog : DialogFragment() {

    private lateinit var coin: Coin

    private val resultLiveDate = SingleLiveEvent<TradeEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coin = arguments?.getSerializable("coin") as Coin
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(activity!!)
                .setTitle("New Trade for ${coin!!.name}")
                .setView(R.layout.dialog_new_trade)
                .setCancelable(false)
                .setPositiveButton("make trade", { dialogInterface, i ->
                    val amountField: EditText = dialog.findViewById(R.id.coin_details_edit_text_coin_amount)
                    val priceField: EditText = dialog.findViewById(R.id.coin_detais_edit_text_coin_price)

                    val amount = amountField.text.toString()
                    val price = priceField.text.toString()

                    if (!amount.isEmpty() && !price.isEmpty()) {
                        val date = Date()
                        date.hours -= 2
                        val tradeEntity = TradeEntity(
                                coinId = coin.id,
                                amount = amount,
                                priceUsd = price,
                                tradedAt = date.formatFullDate()
                        )

                        resultLiveDate.setSingleValue(tradeEntity)
                    }
                })
                .setNegativeButton("cancel", { dialogInterface, i -> dismiss() })

        return alertDialog.create()
    }

    fun getResultLiveDate(): LiveData<TradeEntity> {
        return resultLiveDate
    }
}