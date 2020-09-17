package com.compass.ui.compass

import com.compass.di.scopes.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompassModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun compassFragment() : CompassFragment
}