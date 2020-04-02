package com.currencyconverter.data.repo.implementations

import com.currencyconverter.data.api.RevolutAPI
import com.currencyconverter.data.dto.CurrenciesListResponse
import com.currencyconverter.data.repo.interfaces.RevolutRepo
import com.currencyconverter.di.scopes.ActivityScope
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

@ActivityScope
class RevolutRepoImpl @Inject constructor(
    mRetrofit: Retrofit
) : RevolutRepo {

    private val mService = mRetrofit.create(RevolutAPI::class.java)

    override fun getCurrenciesRates(baseCurrencyCountry: String): Single<CurrenciesListResponse> =
        mService.getCurrencyRates(baseCurrencyCountry)

}