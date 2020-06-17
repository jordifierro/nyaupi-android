package com.nyaupi.presentation

import android.app.Application
import com.nyaupi.data.DataModule


class NyaupiApplication() : Application() {

    companion object {
        lateinit var injector: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        injector = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .dataModule(DataModule())
            .build()
    }
}
