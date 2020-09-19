package com.compass.data.source.orientation

import android.app.Application
import com.compass.sensors.Sensors
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import dagger.Module
import dagger.Provides

@Module
class CompassOrientationSourceModule {
    @AppScoped
    @Provides
    internal fun provideSensors(context: Application) : Sensors{
        return Sensors(context)
    }
}