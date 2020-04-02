package com.currencyconverter.di

import com.currencyconverter.di.modules.FragmentsModule
import com.currencyconverter.di.scopes.ActivityScope
import com.currencyconverter.ui.fragments.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsProvider {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryProvider::class, FragmentsModule::class])
    abstract fun provideMainFragment(): MainFragment
}