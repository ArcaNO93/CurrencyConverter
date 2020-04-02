package com.currencyconverter.di

import androidx.lifecycle.ViewModelProvider
import com.currencyconverter.di.scopes.ActivityScope
import com.currencyconverter.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryProvider {

    @Binds
    @ActivityScope
    internal abstract fun provideViewModelsFactory(ViewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}