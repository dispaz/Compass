package com.compass.data.source.location

import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.inspiringteam.reactivecompass.data.models.GeoPosition
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import com.patloew.rxlocation.RxLocation
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AppScoped
class CompassLocationSource @Inject constructor(private val locationProvider: RxLocation) : LocationDataSource{
    private val locationRequest : LocationRequest
        get(){
            return LocationRequest().apply {
                interval = 5000
                fastestInterval = 5000
                smallestDisplacement = 1f
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        }

    override fun getLocation(): Flowable<GeoPosition> {
        return locationProvider.location().updates(locationRequest)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {position -> Observable.just(GeoPosition(position.latitude, position.longitude))}
            .toFlowable(BackpressureStrategy.LATEST)

    }

}