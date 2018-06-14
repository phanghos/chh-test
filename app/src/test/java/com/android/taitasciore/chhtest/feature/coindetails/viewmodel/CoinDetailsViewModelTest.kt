package com.android.taitasciore.chhtest.feature.coindetails.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.android.taitasciore.chhtest.data.repository.CoinRepository
import com.android.taitasciore.chhtest.data.repository.PortfolioRepository
import com.android.taitasciore.chhtest.domain.model.HistoricalItem
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsActionProcessor
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsUiEvent
import com.android.taitasciore.chhtest.feature.coindetails.CoinDetailsViewState
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

class CoinDetailsViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var coinRepository: CoinRepository

    @Mock
    lateinit var portfolioRepository: PortfolioRepository

    private lateinit var coinDetailsStateReducer: CoinDetailsStateReducer

    private lateinit var coinDetailsActionProcessor: CoinDetailsActionProcessor

    private lateinit var viewModel: CoinDetailsViewModel

    @Before
    fun setUp() {
        initMocks(this)
        coinDetailsActionProcessor = CoinDetailsActionProcessor(coinRepository, portfolioRepository)
        coinDetailsStateReducer = spy(CoinDetailsStateReducer::class.java)
        viewModel = CoinDetailsViewModel(coinDetailsActionProcessor, coinDetailsStateReducer)

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `processUiEvents when Initial event yields success`() {
        // Given
        val observer = mock(Observer::class.java)
        val historicalItem = HistoricalItem()
        val historicalItemList = listOf(historicalItem)
        val expected = CoinDetailsViewState(
                loading = false,
                historical = historicalItemList,
                error = null,
                showErrorLayout = false,
                showSuccessToast = false,
                showErrorToast = false
        )
        `when`(coinRepository.getCoinHistorical(1)).thenReturn(Observable.just(historicalItemList))

        // When
        viewModel.processUiEvents(Observable.just(CoinDetailsUiEvent.Initial(1)))
        viewModel.states().observeForever(observer as Observer<CoinDetailsViewState>)

        // Then
        verify(coinRepository).getCoinHistorical(1)
        verify(observer).onChanged(expected)
        val result = viewModel.states().value
        assertEquals(expected, result)
        assertEquals(result?.historical, historicalItemList)
    }

    @Test
    fun `processUiEvents when initial event yields error and historical is null`() {
        // Given
        val observer = mock(Observer::class.java)
        val exception = NullPointerException()
        val expected = CoinDetailsViewState(
                loading = false,
                historical = null,
                error = exception,
                showErrorLayout = true,
                showSuccessToast = false,
                showErrorToast = true
        )
        `when`(coinRepository.getCoinHistorical(1)).thenReturn(Observable.error(exception))

        // When
        viewModel.processUiEvents(Observable.just(CoinDetailsUiEvent.Initial(1)))
        viewModel.states().observeForever(observer as Observer<CoinDetailsViewState>)

        // Then
        verify(coinRepository).getCoinHistorical(1)
        verify(observer).onChanged(expected)
        val result = viewModel.states().value
        assertEquals(expected, result)
        assertEquals(result?.error, exception)
    }

    @Test
    fun `processUiEvents when retry event yields success`() {
        // Given
        val observer = mock(Observer::class.java)
        val historicalItem = HistoricalItem()
        val historicalItemList = listOf(historicalItem)
        val expected = CoinDetailsViewState(
                loading = false,
                historical = historicalItemList,
                error = null,
                showErrorLayout = false,
                showSuccessToast = false,
                showErrorToast = false
        )
        `when`(coinRepository.getCoinHistorical(1)).thenReturn(Observable.just(historicalItemList))

        // When
        viewModel.processUiEvents(Observable.just(CoinDetailsUiEvent.RetryHistorical(1)))
        viewModel.states().observeForever(observer as Observer<CoinDetailsViewState>)

        // Then
        verify(coinRepository).getCoinHistorical(1)
        verify(observer).onChanged(expected)
        val result = viewModel.states().value
        assertEquals(expected, result)
        assertEquals(result?.historical, historicalItemList)
    }

    @Test
    fun `processUiEvent when retry event yields error and historical is null`() {
        // Given
        val observer = mock(Observer::class.java)
        val exception = NullPointerException()
        val expected = CoinDetailsViewState(
                loading = false,
                historical = null,
                error = exception,
                showErrorLayout = true,
                showSuccessToast = false,
                showErrorToast = true
        )
        `when`(coinRepository.getCoinHistorical(1)).thenReturn(Observable.error(exception))

        // When
        viewModel.processUiEvents(Observable.just(CoinDetailsUiEvent.RetryHistorical(1)))
        viewModel.states().observeForever(observer as Observer<CoinDetailsViewState>)

        // Then
        verify(coinRepository).getCoinHistorical(1)
        verify(observer).onChanged(expected)
        val result = viewModel.states().value
        assertEquals(expected, result)
        assertEquals(result?.error, exception)
    }
}