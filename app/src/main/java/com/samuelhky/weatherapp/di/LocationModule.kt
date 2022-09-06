package com.samuelhky.weatherapp.di

import com.samuelhky.weatherapp.data.location.DefaultLocationTracker
import com.samuelhky.weatherapp.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// When we want to use abstractions/interfaces. Using abstract class and binds just makes Hilt generate less code.

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}