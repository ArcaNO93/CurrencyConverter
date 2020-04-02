package com.currencyconverter.data.api

import com.currencyconverter.data.dto.CurrenciesListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RevolutAPI {

    @GET("/api/android/latest")
    fun getCurrencyRates(
        @Query("base") baseCurrency: String
    ): Single<CurrenciesListResponse>
}