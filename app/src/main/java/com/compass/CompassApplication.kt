package com.compass

import android.app.Application
import com.compass.di.DaggerAppComponent
//import com.compass.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class CompassApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}