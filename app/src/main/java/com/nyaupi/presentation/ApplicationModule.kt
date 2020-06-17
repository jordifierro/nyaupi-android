package com.nyaupi.presentation

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApplicationModule(val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Named("io")
    fun providerIOScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named("main")
    fun providerMainScheduler(): Scheduler = AndroidSchedulers.mainThread()
}
