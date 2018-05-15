package com.astimac.coroutines.di

import com.astimac.coroutines.App
import com.astimac.coroutines.di.db.DatabaseModule
import com.astimac.coroutines.di.network.NetworkModule
import com.astimac.coroutines.di.vm.ViewModelFactoryModule
import com.astimac.coroutines.di.vm.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by astimac on 02.03.18..
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityBuilderModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    NetworkModule::class,
    DatabaseModule::class
])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<App>()
}