package com.currencyconverter.di.modules

import androidx.lifecycle.ViewModel
import com.currencyconverter.di.scopes.ActivityScope
import com.currencyconverter.di.scopes.ViewModelKeys
import com.currencyconverter.ui.viewModels.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivitiesModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKeys(MainActivityViewModel::class)
    internal abstract fun provideMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel
}