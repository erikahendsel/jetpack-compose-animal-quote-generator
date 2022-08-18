package com.erikahendsel.cuteanimalquotes.dependencyInjection

import com.erikahendsel.cuteanimalquotes.data.AnimalApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAnimalApi(): AnimalApi {
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AnimalApi.BASE_URL)
            .build()
            .create(AnimalApi::class.java)
    }
}