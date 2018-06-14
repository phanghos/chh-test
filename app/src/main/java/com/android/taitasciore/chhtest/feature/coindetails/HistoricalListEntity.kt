package com.android.taitasciore.chhtest.feature.coindetails

import com.android.taitasciore.chhtest.data.entity.HistoricalItemEntity
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class HistoricalListEntity(
		@PrimaryKey var coinId: Int? = null,
		@field:SerializedName("historical") var historical: RealmList<HistoricalItemEntity>? = null
) : RealmModel {

	constructor() : this(
			coinId = 0,
			historical = null
	)
}