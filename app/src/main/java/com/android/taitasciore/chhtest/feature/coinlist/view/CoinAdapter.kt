package com.android.taitasciore.chhtest.feature.coinlist.view

import android.arch.lifecycle.LiveData
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.taitasciore.chhtest.R
import com.android.taitasciore.chhtest.util.SingleLiveEvent
import com.android.taitasciore.chhtest.domain.model.Coin
import com.jakewharton.rxbinding2.view.RxView

class CoinAdapter : RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {

    private var coins: ArrayList<Coin> = ArrayList()

    private val clickLiveData = SingleLiveEvent<Coin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.coin_item, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = coins[position]
        holder?.apply {
            coinName.text = coin.name
            coinPrice.text = "$${coin.priceUsd}"
            coinChange.text = "${coin.percentChange24h}%"
        }
    }

    fun addCoins(coins: List<Coin>) {
        this.coins.clear()
        coins.forEach { this.coins.add(it) }
        notifyDataSetChanged()
    }

    fun getClickLiveData(): LiveData<Coin> {
        return clickLiveData
    }

    inner class CoinViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val coinName: TextView = view.findViewById(R.id.coin_item_coin_name)

        val coinPrice: TextView = view.findViewById(R.id.text_view_coin_price)

        val coinChange: TextView = view.findViewById(R.id.text_view_coin_percentage_change)

        init {
            RxView.clicks(view).subscribe { clickLiveData.setSingleValue(coins[adapterPosition]) }
        }
    }
}