package com.compass.data.source.location

import com.inspiringteam.reactivecompass.data.models.GeoPosition
import io.reactivex.Flowable

interface LocationDataSource {
    fun getLocation() : Flowable<GeoPosition>
}