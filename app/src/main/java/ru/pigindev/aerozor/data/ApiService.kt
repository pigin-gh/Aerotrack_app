package ru.pigindev.aerozor.data

import retrofit2.http.GET

interface ApiService {
    @GET("eco2")
    suspend fun getECO2(): ECO2Response
}