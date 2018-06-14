package com.android.taitasciore.chhtest.data.entity.mapper

import com.android.taitasciore.chhtest.data.entity.CoinEntity
import com.android.taitasciore.chhtest.domain.model.Coin
import org.junit.Assert.assertEquals
import org.junit.Test

class CoinMapperTest {

    @Test
    fun map_returns_correct_value() {
        // Given
        val coinEntity = CoinEntity(
                id = 1,
                name = "coin",
                symbol = "coin",
                priceUsd = 10f,
                priceBtc = 20f,
                availableSupply = 50,
                totalSupply = 100

        )
        val coinListEntity = listOf(coinEntity)
        val coin = Coin(
                id = 1,
                name = "coin",
                symbol = "coin",
                priceUsd = 10f,
                priceBtc = 20f,
                availableSupply = 50,
                totalSupply = 100
        )
        val expected = listOf(coin)

        // When
        val result = CoinMapper().map(coinListEntity)

        // Then
        assertEquals(expected, result)
    }
}