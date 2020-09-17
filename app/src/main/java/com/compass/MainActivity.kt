package com.compass

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.compass.ui.compass.CompassFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var compassFragment: CompassFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if(appHasPermissions()){
            addCompassFragment()
        }
        else{
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1)
        {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                addCompassFragment()
            }
            else {
                requestPermission()
            }
        }
    }

    fun addCompassFragment(){
        var fragment = supportFragmentManager.findFragmentById(R.id.container) as CompassFragment?
        if(fragment == null){
            fragment = compassFragment
            supportFragmentManager.beginTransaction().apply {
                add(R.id.container, fragment)
                commit()
            }
        }
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 1)
    }

    fun appHasPermissions() : Boolean{
        return checkCoarsePermission() && checkFinePermission()
    }

    fun checkCoarsePermission() : Boolean{
        val result = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkFinePermission() : Boolean{
        val result = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }
}