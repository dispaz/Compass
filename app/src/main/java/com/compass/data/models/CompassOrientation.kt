package com.compass.data.models

class CompassOrientation {
    var destinationDirection: Float = 0.0f
    var lastDestinationDirection: Float = 0.0f
    var polesDirection: Float = 0.0f
    var lastPolesDirection: Float = 0.0f

    constructor() {}

    constructor(
        destinationDirection: Float
        , lastDestinationDirection: Float
        , polesDirection: Float
        , lastPolesDirection: Float
    ){
        this.destinationDirection = destinationDirection
        this.lastDestinationDirection = lastDestinationDirection
        this.polesDirection = polesDirection
        this.lastPolesDirection = lastPolesDirection
    }

}