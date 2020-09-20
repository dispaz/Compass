package com.compass.data.source.orientation

import com.compass.data.models.CompassOrientation
import com.inspiringteam.reactivecompass.data.models.GeoPosition
import io.reactivex.Flowable

interface OrientationDataSource {
    fun getOrientation() : Flowable<CompassOrientation>
    fun updateCurrentLocation(position: GeoPosition)
}