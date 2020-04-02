package com.currencyconverter.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.currencyconverter.di.scopes.ActivityScope
import com.currencyconverter.di.scopes.ViewModelKeys
import com.currencyconverter.ui.viewModels.MainFragmentViewModel
import com.currencyconverter.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentsModule {

    @Binds
    @ActivityScope
    internal abstract fun provideViewModelsFactory(ViewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @ActivityScope
    @Binds
    @IntoMap
    @ViewModelKeys(MainFragmentViewModel::class)
    internal abstract fun provideEditProfileViewModel(viewModel: MainFragmentViewModel): ViewModel
}