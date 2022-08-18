package com.erikahendsel.cuteanimalquotes.data

import retrofit2.http.GET

interface AnimalApi {

    @GET("/randomanimal")
    suspend fun getRandomAnimal(): Animal

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }
}