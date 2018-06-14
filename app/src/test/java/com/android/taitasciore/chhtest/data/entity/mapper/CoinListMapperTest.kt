package com.android.taitasciore.chhtest.data.entity.mapper

import com.android.taitasciore.chhtest.data.entity.CoinEntity
import com.android.taitasciore.chhtest.data.entity.CoinListEntity
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.CoinList
import io.realm.RealmList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy

class CoinListMapperTest {

    @Spy
    lateinit var coinMapper: CoinMapper

    private lateinit var coinListMapper: CoinListMapper

    @Before
    fun setUp() {
        initMocks(this)
        coinListMapper = CoinListMapper(coinMapper)
    }

    @Test
    fun map_returns_correct_value() {
        // Given
        val coinListEntity = listOf(CoinEntity())
        val realmCoinListEntity = RealmList<CoinEntity>()
        realmCoinListEntity.addAll(coinListEntity)
        val coinListEntityObj = CoinListEntity(data = realmCoinListEntity)
        val coinList = listOf(Coin())
        val expected = CoinList(data = coinList)

        // When
        val result = coinListMapper.map(coinListEntityObj)

        // Then
        verify(coinMapper).map(realmCoinListEntity)
        assertEquals(expected, result)
        assertEquals(expected.data, result.data)
    }
}