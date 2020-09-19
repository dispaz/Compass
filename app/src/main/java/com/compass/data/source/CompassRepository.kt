package com.compass.data.source

import com.compass.data.models.CompassOrientation
import com.compass.data.source.orientation.CompassOrientationSource
import com.compass.data.source.orientation.OrientationDataSource
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@AppScoped
class CompassRepository @Inject constructor(private val compassOrientationSource: CompassOrientationSource) : OrientationDataSource {
    override fun getOrientation(): Flowable<CompassOrientation> {
        return compassOrientationSource.getOrientation()
    }

}