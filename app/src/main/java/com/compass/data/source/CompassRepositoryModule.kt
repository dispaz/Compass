package com.compass.data.source

import com.compass.data.source.location.CompassLocationSource
import com.compass.data.source.location.CompassLocationSourceModule
import com.compass.data.source.location.LocationDataSource
import com.compass.data.source.orientation.CompassOrientationSource
import com.compass.data.source.orientation.CompassOrientationSourceModule
import com.compass.data.source.orientation.OrientationDataSource
import com.compass.sensors.Sensors
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import com.patloew.rxlocation.RxLocation
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(CompassOrientationSourceModule::class, CompassLocationSourceModule::class))
class CompassRepositoryModule {
    @Provides
    @AppScoped
    internal fun provideCompassOrientationSource(sensors: Sensors): OrientationDataSource{
        return CompassOrientationSource(sensors)
    }

    @Provides
    @AppScoped
    internal  fun provideCompassLocationSource(location: RxLocation) : LocationDataSource{
        return CompassLocationSource(location)
    }
}