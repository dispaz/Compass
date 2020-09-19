package com.compass.data.source.orientation;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.GetChars;

import com.compass.data.models.CompassOrientation;
import com.compass.sensors.Sensors;
import com.inspiringteam.reactivecompass.data.models.GeoPosition;
import com.inspiringteam.reactivecompass.di.scopes.AppScoped;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AppScoped
public class CompassOrientationSource implements OrientationDataSource {
    private Sensors sensorsClient;

    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];

    private float[] R = new float[9];
    private float[] I = new float[9];

    private float azimuth;

    private float lastPolesAzimuth;

    private float lastDestinationAzimuth;

    private GeoPosition currentPosition = new GeoPosition(0, 0);
    private GeoPosition destination  = new GeoPosition(0, 0);

    @Inject
    public CompassOrientationSource(Sensors sensors) {
        this.sensorsClient = sensors;
    }

    @Override
    public Flowable<CompassOrientation> getOrientation() {
        return sensorsClient.observeSensors(Sensor.TYPE_ACCELEROMETER
                , Sensor.TYPE_MAGNETIC_FIELD
                , SensorManager.SENSOR_DELAY_GAME
                , null
                , BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(sensorEvent -> {
                    CompassOrientation compassOrientation = new CompassOrientation();

                    float alpha = 0.97f;

                    synchronized (this) {
                        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                            for (int i = 0; i < gravity.length; i++) {
                                gravity[i] = alpha * gravity[i] + (1 - alpha) * sensorEvent.values[i];
                            }
                        }

                        if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                            for (int i = 0; i < geomagnetic.length; i++) {
                                geomagnetic[i] = alpha * geomagnetic[i] + (1 - alpha) * sensorEvent.values[i];
                            }
                        }

                        if(SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)){
                            float orientation[] = new float[3];
                            SensorManager.getOrientation(R, orientation);

                            azimuth = (float) Math.toDegrees(orientation[0]);
                            azimuth = (azimuth + 360) % 360;

                            compassOrientation.setPolesDirection(azimuth);
                            compassOrientation.setLastPolesDirection(lastPolesAzimuth);

                            lastPolesAzimuth = azimuth;

                            double destinationAzimuth = azimuth - bearing(currentPosition, destination);

                            compassOrientation.setDestinationDirection((float)destinationAzimuth);

                            compassOrientation.setLastDestinationDirection(lastDestinationAzimuth);
                        }
                    }
                    return Flowable.just(compassOrientation);
                });
    }



    private double bearing(GeoPosition start
                        , GeoPosition end){
        double latRadians1 = Math.toRadians(start.getLatitude());
        double latRadians2 = Math.toRadians(end.getLatitude());
        double lngDiff = Math.toRadians(end.getLongtitude() - start.getLongtitude());
        double y = Math.sin(lngDiff) * Math.cos(latRadians2);
        double x = Math.cos(latRadians1) * Math.sin(latRadians2) - Math.sin(latRadians1) * Math.cos(latRadians2) * Math.cos(lngDiff);

        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }
}
