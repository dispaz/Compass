package com.compass.data.source.orientation

import com.compass.data.models.CompassOrientation
import io.reactivex.rxjava3.core.Flowable

interface OrientationDataSource {
    fun getOrientation() : Flowable<CompassOrientation>
}