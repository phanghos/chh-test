package com.android.taitasciore.chhtest.feature.coinlist.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.android.taitasciore.chhtest.data.repository.CoinRepository
import com.android.taitasciore.chhtest.domain.model.Coin
import com.android.taitasciore.chhtest.domain.model.CoinList
import com.android.taitasciore.chhtest.feature.coinlist.CoinListActionProcessor
import com.android.taitasciore.chhtest.feature.coinlist.CoinListStateReducer
import com.android.taitasciore.chhtest.feature.coinlist.CoinListUiEvent
import com.android.taitasciore.chhtest.feature.coinlist.CoinListViewState
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

class CoinListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var coinRepository: CoinRepository

    private lateinit var coinListActionProcessor: CoinListActionProcessor

    private lateinit var viewModel: CoinListViewModel

    @Before
    fun setUp() {
        initMocks(this)
        coinListActionProcessor = CoinListActionProcessor(coinRepository)
        val coinListStateReducer = CoinListStateReducer()
        viewModel = CoinListViewModel(coinListActionProcessor, coinListStateReducer)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `processUiEvents when initial event yields success`() {
        // Given
        val observer = mock(Observer::class.java)
        val coinList = CoinList(data = listOf(Coin()))
        val expected = Observable.just(coinList)
        `when`(coinRepository.getCoins(1)).thenReturn(expected)

        // When
        viewModel.states().observeForever(observer as Observer<CoinListViewState>)
        viewModel.processUiEvents(Observable.just(CoinListUiEvent.Initial()))

        // Then
        verify(coinRepository).getCoins(1)
        assertEquals(CoinListViewState(
                loading = false,
                coins = listOf(Coin()),
                error = null,
                showError = false,
                showProgress = false,
                showInitialError = false
        ), viewModel.states().value)
    }

    @Test
    fun `processUiEvents when initial event yields error`() {
        // Given
        val observer = mock(Observer::class.java)
        val expectedException = NullPointerException()
        `when`(coinRepository.getCoins(1)).thenReturn(Observable.error(expectedException))

        // When
        viewModel.states().observeForever(observer as Observer<CoinListViewState>)
        viewModel.processUiEvents(Observable.just(CoinListUiEvent.Initial()))

        // Then
        verify(coinRepository).getCoins(1)
        assertEquals(CoinListViewState(
                loading = false,
                coins = null,
                error = expectedException,
                showError = true,
                showProgress = false,
                showInitialError = true
        ), viewModel.states().value)
    }

    @Test
    fun `processUiEvents when load event yields sucess`() {
        // Given
        val observer = mock(Observer::class.java)
        val coinList = CoinList(data = listOf(Coin()))
        val expected = Observable.just(coinList)
        `when`(coinRepository.getCoins(1)).thenReturn(expected)

        // When
        viewModel.states().observeForever(observer as Observer<CoinListViewState>)
        viewModel.processUiEvents(Observable.just(CoinListUiEvent.Load()))

        // Then
        verify(coinRepository).getCoins(1)
        assertEquals(CoinListViewState(
                loading = false,
                coins = listOf(Coin()),
                error = null,
                showError = false,
                showProgress = false,
                showInitialError = false
        ), viewModel.states().value)
    }

    @Test
    fun `processUiEvents when load event yields error`() {
        // Given
        val observer = mock(Observer::class.java)
        val expectedException = NullPointerException()
        `when`(coinRepository.getCoins(1)).thenReturn(Observable.error(expectedException))

        // When
        viewModel.states().observeForever(observer as Observer<CoinListViewState>)
        viewModel.processUiEvents(Observable.just(CoinListUiEvent.Load()))

        // Then
        verify(coinRepository).getCoins(1)
        assertEquals(CoinListViewState(
                loading = false,
                coins = null,
                error = expectedException,
                showError = true,
                showProgress = false,
                showInitialError = true
        ), viewModel.states().value)
    }

    @Test
    fun `processUiEvents when retry event yields sucess`() {
        // Given
        val observer = mock(Observer::class.java)
        val coinList = CoinList(data = listOf(Coin()))
        val expected = Observable.just(coinList)
        `when`(coinRepository.getCoins(1)).thenReturn(expected)

        // When
        viewModel.states().observeForever(observer as Observer<CoinListViewState>)
        viewModel.processUiEvents(Observable.just(CoinListUiEvent.Retry()))

        // Then
        verify(coinRepository).getCoins(1)
        assertEquals(CoinListViewState(
                loading = false,
                coins = listOf(Coin()),
                error = null,
                showError = false,
                showProgress = false,
                showInitialError = false
        ), viewModel.states().value)
    }

    @Test
    fun `processUiEvents when retry event yields error`() {
        // Given
        val observer = mock(Observer::class.java)
        val expectedException = NullPointerException()
        `when`(coinRepository.getCoins(1)).thenReturn(Observable.error(expectedException))

        // When
        viewModel.states().observeForever(observer as Observer<CoinListViewState>)
        viewModel.processUiEvents(Observable.just(CoinListUiEvent.Retry()))

        // Then
        verify(coinRepository).getCoins(1)
        assertEquals(CoinListViewState(
                loading = false,
                coins = null,
                error = expectedException,
                showError = true,
                showProgress = false,
                showInitialError = true
        ), viewModel.states().value)
    }
}