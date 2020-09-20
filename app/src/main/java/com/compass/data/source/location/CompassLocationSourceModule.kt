package com.compass.data.source.location

import android.app.Application
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides

@Module
class CompassLocationSourceModule {
    @AppScoped
    @Provides
    fun provideLocation(context: Application): RxLocation {
        return RxLocation(context)
    }
}