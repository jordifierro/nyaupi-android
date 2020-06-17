package com.nyaupi.presentation

import com.nyaupi.data.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class))
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
}
