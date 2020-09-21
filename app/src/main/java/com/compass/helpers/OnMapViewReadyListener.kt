package com.compass.helpers

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class OnMapViewReadyListener(private val mapFragment: SupportMapFragment, private val toBeNotified: OnGlobalLayoutAndMapReadyListener ) : ViewTreeObserver.OnGlobalLayoutListener,
    OnMapReadyCallback {
    private val mapView: View? = mapFragment.view

    private var isViewReady = false
    private var isMapReady = false
    private var map: GoogleMap? = null

    interface OnGlobalLayoutAndMapReadyListener {
        fun onMapReady(googleMap: GoogleMap?)
    }

    init {
        registerListeners()
    }

    private fun registerListeners() {
        // View layout.
        if (mapView?.width != 0 && mapView?.height != 0) {
            // View has already completed layout.
            isViewReady = true
        } else {
            // Map has not undergone layout, register a View observer.
            mapView.viewTreeObserver.addOnGlobalLayoutListener(this)
        }
        // GoogleMap. Note if the GoogleMap is already ready it will still fire the callback later.
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        // NOTE: The GoogleMap API specifies the listener is removed just prior to invocation.
        map = googleMap ?: return
        isMapReady = true
        fireCallbackIfReady()
    }

    // We use the new method when supported
    @Suppress("DEPRECATION")
    @SuppressLint("NewApi")  // We check which build version we are using.
    override fun onGlobalLayout() {
        // Remove our listener.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            mapView?.viewTreeObserver?.removeGlobalOnLayoutListener(this)
        } else {
            mapView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
        }
        isViewReady = true
        fireCallbackIfReady()
    }

    private fun fireCallbackIfReady() {
        if (isViewReady && isMapReady) {
            toBeNotified.onMapReady(map)
        }
    }
}