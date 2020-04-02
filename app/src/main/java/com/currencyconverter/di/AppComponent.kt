package com.currencyconverter.di

import com.currencyconverter.di.modules.AppModule
import com.currencyconverter.utils.ComponentProvider
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component (modules = [
    AppModule::class,
    AndroidSupportInjectionModule::class,
    ActivityProvider::class,
    FragmentsProvider::class
])

@Singleton
interface AppComponent : AndroidInjector<ComponentProvider> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<ComponentProvider>
}