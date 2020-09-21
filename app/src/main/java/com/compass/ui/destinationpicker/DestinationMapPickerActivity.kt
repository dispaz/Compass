package com.compass.ui.destinationpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.compass.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.fragment_destination_map_picker.*
import javax.inject.Inject
class DestinationMapPickerActivity : DaggerAppCompatActivity()  {

    @Inject lateinit var injectedFragment: DestinationMapPickerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_map_picker)

        var mapFragment =
            supportFragmentManager.findFragmentById(R.id.contentFrame) as DestinationMapPickerFragment?
        if(mapFragment == null) {
            mapFragment = injectedFragment
            val transaction = supportFragmentManager.beginTransaction().apply {
                add(R.id.contentFrame, mapFragment)
                commit()
            }
        }
    }
}