package com.compass.data.source.orientation

import com.compass.data.models.CompassOrientation
import com.inspiringteam.reactivecompass.data.models.GeoPosition
import io.reactivex.Flowable
import io.reactivex.Single

interface OrientationDataSource {
    fun getOrientation() : Flowable<CompassOrientation>
    fun getDestinationPosition(): Single<GeoPosition>
    fun getDestinationDistance(): Flowable<Float>
    fun updateCurrentLocation(position: GeoPosition)
    fun updateDestinationPosition(location: GeoPosition)
}