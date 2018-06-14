package com.android.taitasciore.chhtest.data.entity.mapper

import com.android.taitasciore.chhtest.data.entity.UserCoinEntity
import com.android.taitasciore.chhtest.domain.model.UserCoin
import javax.inject.Inject

open class UserPortfolioMapper @Inject constructor() : Mapper<List<UserCoinEntity>, List<UserCoin>> {

    override fun map(input: List<UserCoinEntity>): List<UserCoin> {
        return input.map { map(it) }
    }

    private fun map(userCoinEntity: UserCoinEntity): UserCoin {
        return UserCoin(
                priceUsd = userCoinEntity.priceUsd,
                amount = userCoinEntity.amount,
                coinId = userCoinEntity.coinId
        )
    }
}