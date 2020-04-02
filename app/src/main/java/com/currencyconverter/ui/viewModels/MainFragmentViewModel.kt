package com.currencyconverter.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.currencyconverter.business.implementations.RevolutUseCasesImpl
import com.currencyconverter.data.dto.BaseCurrency
import com.currencyconverter.di.scopes.ActivityScope
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ActivityScope
class MainFragmentViewModel @Inject constructor(
    private val mRevolutUseCases: RevolutUseCasesImpl
) : ViewModel() {

    private lateinit var mDisposable: Disposable

    private val _mCurrenciesListUpdate = MutableLiveData<Any>()
    val mCurrenciesListUpdate: LiveData<Any>
        get() = _mCurrenciesListUpdate

    fun getCurrenciesRates() {
        mDisposable = Flowable.interval(1, TimeUnit.SECONDS).flatMap {
            mRevolutUseCases.getCurrenciesRates(BaseCurrency.country)
                .toFlowable()
        }
            .retryWhen {
                it.flatMap {
                    Flowable.timer(1, TimeUnit.SECONDS)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _mCurrenciesListUpdate.postValue(null)
            }
    }

    fun forceListUpdate() =
        mRevolutUseCases.forceListUpdate()

    fun unsubscribe() {
        if (!mDisposable.isDisposed)
            mDisposable.dispose()
    }
}