package com.android.taitasciore.chhtest.data.entity

import com.google.gson.annotations.SerializedName

data class CoinListResponseEntity(

	@field:SerializedName("coins")
	val coins: CoinListEntity? = null
)