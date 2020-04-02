package com.currencyconverter.data.repo.interfaces

import com.currencyconverter.data.dto.CurrenciesListResponse
import io.reactivex.Single

interface RevolutRepo {
    fun getCurrenciesRates(baseCurrencyCountry: String): Single<CurrenciesListResponse>
}