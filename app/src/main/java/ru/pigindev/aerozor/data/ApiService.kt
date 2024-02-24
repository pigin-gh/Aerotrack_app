package ru.pigindev.aerozor.data

import retrofit2.http.GET

interface ApiService {
    @GET("lastval/eco2")
    suspend fun getECO2(): LastvalResponse

    @GET("lastval/temp")
    suspend fun getTemp(): LastvalResponse

    @GET("lastval/hum")
    suspend fun getHumidity(): LastvalResponse
}