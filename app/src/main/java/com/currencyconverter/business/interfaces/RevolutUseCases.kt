package com.currencyconverter.business.interfaces

import com.currencyconverter.data.dto.CurrenciesListResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface RevolutUseCases {
    fun getCurrenciesRates(baseCurrencyCountry: String): Single<CurrenciesListResponse>
    fun forceListUpdate()
    fun initialLoading()
    fun updateList()
}