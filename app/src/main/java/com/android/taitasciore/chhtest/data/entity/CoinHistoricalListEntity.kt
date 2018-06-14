package com.android.taitasciore.chhtest.data.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CoinHistoricalListEntity(
		@PrimaryKey var coinId: Int,
		@field:SerializedName("historical") var historical: RealmList<HistoricalItemEntity>? = null
) : RealmModel {

	constructor() : this(
			coinId = 0,
			historical = null
	)
}