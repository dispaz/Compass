package com.compass.data.source

import com.compass.data.source.orientation.CompassOrientationSource
import com.compass.data.source.orientation.CompassOrientationSourceModule
import com.compass.data.source.orientation.OrientationDataSource
import com.compass.sensors.Sensors
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(CompassOrientationSourceModule::class))
class CompassRepositoryModule {
    @Provides
    @AppScoped
    internal fun provideCompassOrientationSource(sensors: Sensors): OrientationDataSource{
        return CompassOrientationSource(sensors)
    }
}