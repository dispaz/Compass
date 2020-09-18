package com.compass.sensors

import android.content.AsyncQueryHandler
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.icu.number.NumberFormatter
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableOnSubscribe
import java.lang.Exception
import java.util.logging.Handler

class Sensors constructor(context: Context) {
    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val sensors: List<Sensor>
        get() = sensorManager.getSensorList(Sensor.TYPE_ALL)

    fun sensorAvailable(sensorType: Int): Boolean {
        return sensorManager.getDefaultSensor(sensorType) != null
    }

    fun observeSensor(
        sensorType: Int, samplingPeriod: Int = SensorManager.SENSOR_DELAY_NORMAL,
        handler: Handler? = null, strategy: BackpressureStrategy = BackpressureStrategy.BUFFER
    ): Flowable<SensorEvent> {
        if (!sensorAvailable(sensorType)) {
            return Flowable.error(Exception("${sensorType} is not available"))
        }

        val sensor = sensorManager.getDefaultSensor(sensorType)

        return Flowable.create(FlowableOnSubscribe<SensorEvent>
        {
            emitter ->
            val listener = object : SensorEventListener {
                override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                    TODO("Not yet implemented")
                }

                override fun onSensorChanged(event: SensorEvent?) {
                    if (emitter != null){
                        emitter!!.onNext(event)
                    }
                }
            }

            if(handler != null){
                sensorManager.registerListener(listener, sensor, samplingPeriod)
            }
            else{
                sensorManager.registerListener(listener, sensor, samplingPeriod)
            }
        }, strategy)
    }
}