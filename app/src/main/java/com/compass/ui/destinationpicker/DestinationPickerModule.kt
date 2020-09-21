package com.compass.ui.destinationpicker

import com.compass.di.scopes.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DestinationPickerModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun destinationPickerFragment(): DestinationMapPickerFragment
}