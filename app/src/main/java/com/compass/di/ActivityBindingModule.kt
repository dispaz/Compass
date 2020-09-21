package com.compass.di

import com.compass.MainActivity
import com.compass.di.scopes.ActivityScoped
import com.compass.ui.compass.CompassModule
import com.compass.ui.destinationpicker.DestinationMapPickerActivity
import com.compass.ui.destinationpicker.DestinationPickerModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [CompassModule::class])
    internal abstract fun compassActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [DestinationPickerModule::class])
    internal abstract fun destinationPickerActivity() : DestinationMapPickerActivity
}