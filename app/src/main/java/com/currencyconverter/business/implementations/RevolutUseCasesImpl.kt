package com.currencyconverter.business.implementations

import com.currencyconverter.business.interfaces.RevolutUseCases
import com.currencyconverter.data.dto.*
import com.currencyconverter.data.repo.implementations.RevolutRepoImpl
import com.currencyconverter.di.scopes.ActivityScope
import com.currencyconverter.utils.format
import com.currencyconverter.utils.round
import io.reactivex.Single
import javax.inject.Inject

@ActivityScope
class RevolutUseCasesImpl @Inject constructor(
    private val mRevolutRepo: RevolutRepoImpl
) : RevolutUseCases {

    private var mCurrenciesListResponse = CurrenciesListResponse()
    private var mFirstStart = true

    override fun getCurrenciesRates(baseCurrencyCountry: String): Single<CurrenciesListResponse> =
        mRevolutRepo.getCurrenciesRates(baseCurrencyCountry).doOnSuccess {
            mCurrenciesListResponse = it
            if (mFirstStart) {
                initialLoading()
                mFirstStart = false
            } else
                updateList()
        }

    override fun forceListUpdate() =
        updateList()

    override fun initialLoading() {
        CurrenciesList.currenciesList.clear()
        CurrenciesList.currenciesList.add(
            0, Currency(
                BaseCurrency.country,
                true,
                value = BaseCurrency.value.toString().format()
            )
        )
        CurrenciesList.currenciesList.addAll(mCurrenciesListResponse.rates.toList().map {
            Currency(
                country = it.first,
                base = false,
                value = (it.second * BaseCurrency.value).round(2).toString().format()
            )
        })
    }

    override fun updateList() {
        mCurrenciesListResponse.rates.forEach { currency ->
                CurrenciesList.currenciesList.find {
                    it.country == currency.key && it.country != BaseCurrency.country
                }?.value = (currency.value * BaseCurrency.value).round(2).toString().format()
        }
    }


}