package com.compass.ui.compass

import com.inspiringteam.reactivecompass.data.models.GeoPosition

class LocationModel {
    var geoPosition: GeoPosition

    constructor(geoPosition: GeoPosition){
        this.geoPosition = geoPosition
    }
}