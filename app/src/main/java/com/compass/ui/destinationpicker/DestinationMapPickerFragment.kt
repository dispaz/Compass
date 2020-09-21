package com.compass.ui.destinationpicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.compass.R
import com.compass.databinding.FragmentDestinationMapPickerBinding
import com.compass.helpers.OnMapViewReadyListener
import com.compass.ui.compass.CompassViewModel
import com.compass.ui.compass.models.LocationUiModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.inspiringteam.reactivecompass.data.models.GeoPosition
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_destination_map_picker.*
import javax.inject.Inject


class DestinationMapPickerFragment @Inject constructor() : DaggerFragment(),
    OnMapViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    @Inject
    lateinit var viewModel: CompassViewModel

    private lateinit var binding: FragmentDestinationMapPickerBinding

    private var subscriptions = CompositeDisposable()

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDestinationMapPickerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        if (mapFragment != null) {
            OnMapViewReadyListener(mapFragment, this)
        }

        submitDestinationButton.setOnClickListener{
            submitDestination()
            backToCompassPage()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            map = googleMap

            map.isMyLocationEnabled = true

            subscriptions.add(viewModel.getLocationUiModel().subscribe(
                { uiModel -> this.focusOnLocation(uiModel) },
                { showErrorMessage("error loading directions") }
            ))

            map.setOnMapClickListener {
                setDestination(it)
            }
        }
    }

    private fun focusOnLocation(locationUiModel: LocationUiModel) {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(locationUiModel.location.latitude, locationUiModel.location.longitude))
            .zoom(15f)
            .build()
        val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.animateCamera(cu)
    }

    private fun showErrorMessage(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    private fun setDestination(latLng: LatLng){
        map.clear()
        map.addMarker(MarkerOptions().position(latLng))
        binding.latEditText.setText(latLng.latitude.toString(), TextView.BufferType.EDITABLE)
        binding.lngEditText.setText(latLng.longitude.toString(), TextView.BufferType.EDITABLE)

    }

    private fun submitDestination(){

        val latText = binding.latEditText.getText().toString()
        val lngText = binding.lngEditText.getText().toString()

        var location = GeoPosition(java.lang.Double.parseDouble(latText),
            java.lang.Double.parseDouble(lngText))

        viewModel.setDestinationLocation(location)
    }

    private fun backToCompassPage() {
        activity?.finish()
    }
}