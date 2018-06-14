package com.android.taitasciore.chhtest.data.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class UserPortfolioEntity(
	@field:SerializedName("coins") var coins: RealmList<UserCoinEntity>? = null
) : RealmModel