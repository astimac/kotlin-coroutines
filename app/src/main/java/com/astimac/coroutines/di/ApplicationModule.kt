package com.astimac.coroutines.di

import android.content.Context
import com.astimac.coroutines.App
import dagger.Module
import dagger.Provides

@Module()
class ApplicationModule {
    @Provides
    fun provideContext(application: App): Context = application.applicationContext
}