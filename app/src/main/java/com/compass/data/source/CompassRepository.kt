package com.compass.data.source

import com.compass.data.models.CompassOrientation
import com.compass.data.source.location.CompassLocationSource
import com.compass.data.source.location.LocationDataSource
import com.compass.data.source.orientation.CompassOrientationSource
import com.compass.data.source.orientation.OrientationDataSource
import com.inspiringteam.reactivecompass.data.models.GeoPosition
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import io.reactivex.Flowable
import javax.inject.Inject

@AppScoped
class CompassRepository @Inject constructor(
    private val compassOrientationSource: CompassOrientationSource,
    private val compassLocationSource: CompassLocationSource
) : OrientationDataSource, LocationDataSource {
    override fun getOrientation(): Flowable<CompassOrientation> {
        return compassOrientationSource.getOrientation()
    }

    override fun getLocation(): Flowable<GeoPosition> {
        return compassLocationSource.getLocation()
            .flatMap { geoPosition ->
                updateCurrentLocation(geoPosition)
                Flowable.just(geoPosition)
            }
    }

    override fun updateCurrentLocation(position: GeoPosition){
        compassOrientationSource.updateCurrentLocation(position)
    }


}