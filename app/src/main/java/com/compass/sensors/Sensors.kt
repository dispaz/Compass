package com.compass.sensors

import android.content.AsyncQueryHandler
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.icu.number.NumberFormatter
import java.lang.Exception
import android.os.Handler
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe

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

        return Flowable.create(
            FlowableOnSubscribe<SensorEvent>
            { emitter ->
                val listener = object : SensorEventListener {
                    override fun onAccuracyChanged(p0: Sensor, p1: Int) {
                        TODO("Not yet implemented")
                    }

                    override fun onSensorChanged(event: SensorEvent) {
                        if (emitter != null) {
                            emitter!!.onNext(event)
                        }
                    }
                }

                if (handler != null) {
                    sensorManager.registerListener(listener, sensor, samplingPeriod, handler)
                } else {
                    sensorManager.registerListener(listener, sensor, samplingPeriod)
                }
            }, strategy
        )
    }

    fun observeSensors(
        sensorType1: Int, sensorType2: Int, samplingPeriod: Int,
        handler: Handler?, strategy: BackpressureStrategy = BackpressureStrategy.BUFFER
    ): Flowable<SensorEvent> {
        if (!sensorAvailable(sensorType1) || !sensorAvailable(sensorType2)) {
            return Flowable.error(Exception("${sensorType1} and ${sensorType2} sensors are not available"))
        }

        val sensor1 = sensorManager.getDefaultSensor(sensorType1)
        val sensor2 = sensorManager.getDefaultSensor(sensorType2)

        return Flowable.create(FlowableOnSubscribe<SensorEvent> { emitter ->
            val listener = object : SensorEventListener {
                override fun onAccuracyChanged(p0: Sensor, p1: Int) {
                    //TODO("Not yet implemented")
                }

                override fun onSensorChanged(event: SensorEvent) {
                    if (emitter != null) {
                        emitter!!.onNext(event)
                    }
                }
            }

            if (handler != null) {
                sensorManager.registerListener(listener, sensor1, samplingPeriod)
                sensorManager.registerListener(listener, sensor2, samplingPeriod)
            } else {
                sensorManager.registerListener(listener, sensor1, samplingPeriod, handler)
                sensorManager.registerListener(listener, sensor2, samplingPeriod, handler)
            }

        }, strategy)
    }
}
