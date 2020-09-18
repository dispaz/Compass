package com.compass.ui.compass

import androidx.lifecycle.ViewModel
import com.compass.data.source.CompassRepository
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@AppScoped
class CompassViewModel @Inject constructor(val repository: CompassRepository): ViewModel() {
    fun getCurrentLocationModel() : Flowable<LocationModel>{
        TODO("Not implemented")
    }
}