package com.samuelhky.weatherapp.di

import com.samuelhky.weatherapp.data.repository.WeatherRepositoryImpl
import com.samuelhky.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// When we want to use abstractions/interfaces. Using abstract class and binds just makes Hilt generate less code.

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}