package com.currencyconverter.di

import com.currencyconverter.di.modules.ActivitiesModule
import com.currencyconverter.di.scopes.ActivityScope
import com.currencyconverter.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityProvider {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModelFactoryProvider::class, ActivitiesModule::class])
    abstract fun provideMainActivity(): MainActivity
}