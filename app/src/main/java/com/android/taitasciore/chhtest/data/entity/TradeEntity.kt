package com.android.taitasciore.chhtest.data.entity

import com.google.gson.annotations.SerializedName

data class TradeEntity(

	@field:SerializedName("price_usd")
	val priceUsd: String? = null,

	@field:SerializedName("amount")
	val amount: String? = null,

	@field:SerializedName("notes")
	val notes: Any? = null,

	@field:SerializedName("coin_id")
	val coinId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("traded_at")
	val tradedAt: String? = null,

	@field:SerializedName("total_usd")
	val totalUsd: Double? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)