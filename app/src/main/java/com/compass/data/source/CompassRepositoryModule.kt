package com.compass.data.source

import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import dagger.Module
import dagger.Provides

@Module
class CompassRepositoryModule {
    @Provides
    @AppScoped
    fun testFunc(): String{ return "test"}
}