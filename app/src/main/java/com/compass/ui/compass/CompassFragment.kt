package com.compass.ui.compass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.compass.R
import com.compass.databinding.CompassFragmentBinding
import com.compass.di.scopes.ActivityScoped
import com.compass.ui.compass.models.DirectionsUiModel
import dagger.android.support.DaggerFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScoped
class CompassFragment @Inject constructor() : DaggerFragment() {
    @Inject
    lateinit var viewModel: CompassViewModel

    private var subscriptions = CompositeDisposable()

    private lateinit var binding: CompassFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompassFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        bindViewModel()
    }

    fun bindViewModel() {
        subscriptions = CompositeDisposable()

        subscriptions.add(viewModel.getCompassUiModel().subscribe(
            { uiModel -> this.updateDirections(uiModel) },
            { showErrorMessage("error loading directions") }
        ))
    }

    fun updateDirections(directionsUiModel: DirectionsUiModel) {
        val compassOrientation = directionsUiModel.compassOrientation
        rotateDirectionView(compassOrientation.polesDirection, compassOrientation.lastPolesDirection, binding.compass)
        binding.compassDeegree.text = "${compassOrientation.polesDirection.toInt()}°"
    }

    fun showErrorMessage(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    private fun rotateDirectionView(azimuth: Float, currentAzimuth: Float, targetView: View) {
        val anim = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )

        anim.duration = 100
        anim.repeatCount = 0
        anim.fillAfter = true
        targetView.startAnimation(anim)
    }

}