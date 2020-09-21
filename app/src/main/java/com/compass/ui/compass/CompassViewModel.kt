package com.compass.ui.compass

import android.location.Location
import androidx.lifecycle.ViewModel
import com.compass.data.models.CompassOrientation
import com.compass.data.source.CompassRepository
import com.compass.ui.compass.models.DirectionsUiModel
import com.compass.ui.compass.models.LocationUiModel
import com.inspiringteam.reactivecompass.data.models.GeoPosition
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

@AppScoped
class CompassViewModel @Inject constructor(val repository: CompassRepository): ViewModel() {
    fun getCompassUiModel() : Flowable<DirectionsUiModel> {
        return repository.getOrientation().map<DirectionsUiModel> { constructCompassUiModel(it) }
    }

    fun getLocationUiModel() : Flowable<LocationUiModel>{
        return repository.getLocation().map<LocationUiModel> { constructLocationUiModel(it) }
    }

    fun getDestinationLocationUiModel() : Single<LocationUiModel>? {
        return repository.getDestinationPosition().map<LocationUiModel> { constructLocationUiModel(it) }
    }

    fun getDestinationDistance() : Flowable<Float>{
        return repository.getDestinationDistance().map<Float>{it}
    }

    private fun constructCompassUiModel(compassOrientation: CompassOrientation) : DirectionsUiModel{
        return DirectionsUiModel(compassOrientation)
    }

    private fun constructLocationUiModel(location: GeoPosition) : LocationUiModel{
        return LocationUiModel(location)
    }

    fun setDestinationLocation(location: GeoPosition){
        repository.updateDestinationPosition(location)
    }
}