package com.inspiringteam.reactivecompass.data.models

class GeoPosition {
    var latitude = 0.0
    var longitude = 0.0

    constructor() {}
    constructor(latitude: Double, longtitude: Double) {
        this.latitude = latitude
        this.longitude = longtitude
    }

}