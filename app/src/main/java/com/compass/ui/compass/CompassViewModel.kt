package com.compass.ui.compass

import androidx.lifecycle.ViewModel
import com.compass.data.models.CompassOrientation
import com.compass.data.source.CompassRepository
import com.compass.ui.compass.models.DirectionsUiModel
import com.inspiringteam.reactivecompass.data.models.GeoPosition
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@AppScoped
class CompassViewModel @Inject constructor(val repository: CompassRepository): ViewModel() {
    fun getCompassUiModel() : Flowable<DirectionsUiModel>{
        return repository.getOrientation().map<DirectionsUiModel> { constructCompassUiModel(it) }
    }

    private fun constructCompassUiModel(compassOrientation: CompassOrientation) : DirectionsUiModel{
        return DirectionsUiModel(compassOrientation)
    }

    fun setDestinationLocation(location: GeoPosition){
        //
    }
}