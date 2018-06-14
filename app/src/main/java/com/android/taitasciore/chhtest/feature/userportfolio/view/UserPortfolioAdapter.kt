package com.android.taitasciore.chhtest.feature.userportfolio.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.taitasciore.chhtest.R
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.UserCoin

class UserPortfolioAdapter(private val coins: List<Coin>) : RecyclerView.Adapter<UserPortfolioAdapter.PortfolioViewHolder>() {

    private val userCoins = mutableListOf<UserCoin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.user_coin_item, parent, false)
        return PortfolioViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userCoins.size
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val coin = userCoins[position]
        holder?.coinName.text = getCoinNameById(coin.coinId!!) ?: "Unknown coin"
        holder?.coinPrice.text = "$${coin.priceUsd}"
        holder?.coinAmount.text = "$${coin.amount}"
    }

    fun add(userCoins: List<UserCoin>) {
        this.userCoins.addAll(userCoins)
        notifyDataSetChanged()
    }

    private fun getCoinNameById(coinId: Int): String? {
        coins.forEach { if (coinId == it.id) return it.name }
        return null
    }

    inner class PortfolioViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val coinName: TextView = view.findViewById(R.id.portfolio_text_view_coin_name)

        val coinPrice: TextView = view.findViewById(R.id.portfolio_text_view_coin_price)
        
        val coinAmount: TextView = view.findViewById(R.id.portfoli_text_view_coin_amount)
    }
}