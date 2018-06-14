package com.android.taitasciore.chhtest.data.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CoinEntity(

	@field:SerializedName("price_usd")
	var priceUsd: Float? = null,

	@field:SerializedName("symbol")
	var symbol: String? = null,

	@field:SerializedName("24h_volume_usd")
	var jsonMember24hVolumeUsd: Long? = null,

	@field:SerializedName("created_at")
	var createdAt: String? = null,

	@field:SerializedName("price_btc")
	var priceBtc: Float? = null,

	@field:SerializedName("available_supply")
	var availableSupply: Long? = null,

	@field:SerializedName("total_supply")
	var totalSupply: Long? = null,

	@field:SerializedName("market_cap_usd")
	var marketCapUsd: Long? = null,

	@field:SerializedName("percent_change_1h")
	var percentChange1h: String? = null,

	@field:SerializedName("percent_change_24h")
	var percentChange24h: String? = null,

	@field:SerializedName("percent_change_7d")
	var percentChange7d: String? = null,

	@field:SerializedName("updated_at")
	var updatedAt: String? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("rank")
	var rank: Int? = null,

	@field:SerializedName("id")
	@PrimaryKey
	var id: Int? = null
) : RealmModel