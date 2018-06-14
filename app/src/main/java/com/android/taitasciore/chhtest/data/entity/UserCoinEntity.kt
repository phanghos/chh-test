package com.android.taitasciore.chhtest.data.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class UserCoinEntity(
	@field:SerializedName("price_usd") var priceUsd: Float? = null,
	@field:SerializedName("amount") var amount: Float? = null,
	@field:SerializedName("coin_id") var coinId: Int? = null
) : RealmModel