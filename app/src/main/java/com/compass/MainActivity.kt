package com.compass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.compass.ui.compass.CompassFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CompassFragment.newInstance())
                    .commitNow()
        }
    }
}