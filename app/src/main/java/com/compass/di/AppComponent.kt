package com.compass.di

import android.app.Application
import com.compass.CompassApplication
import com.compass.data.source.CompassRepositoryModule
import com.inspiringteam.reactivecompass.di.scopes.AppScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(modules = [AppModule::class
                    , ActivityBindingModule::class
                    , CompassRepositoryModule::class
                    , AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<CompassApplication>{
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application) : AppComponent.Builder

        fun build() : AppComponent
    }
}