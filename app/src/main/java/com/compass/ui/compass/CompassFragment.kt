package com.compass.ui.compass

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.compass.R
import com.compass.di.scopes.ActivityScoped
import dagger.android.support.DaggerFragment
import javax.inject.Inject

@ActivityScoped
class CompassFragment @Inject constructor() : DaggerFragment() {

    companion object {
        fun newInstance() = CompassFragment()
    }

    private lateinit var viewModel: CompassViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.compass_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CompassViewModel::class.java)
        // TODO: Use the ViewModel
    }

}