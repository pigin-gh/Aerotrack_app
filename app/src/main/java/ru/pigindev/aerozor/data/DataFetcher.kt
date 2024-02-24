package ru.pigindev.aerozor.data

import android.util.Log
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pigindev.aerozor.ui.home.HomeFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DataFetcher(private val fragment: HomeFragment) {

    private val apiService: ApiService

    private var job: Job? = null

    init {
        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://77.232.138.28:8000/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        fetchNumber()
    }

    private fun fetchNumber() {
        job = CoroutineScope(Dispatchers.IO).launch {

            while (isActive) {
                try {
                    val co2Response = apiService.getECO2()
                    val tempResponse = apiService.getTemp()
                    val humResponse = apiService.getHumidity()

                    withContext(Dispatchers.Main) {
                        fragment.updateTemp(temp = tempResponse.value)
                        fragment.updateCO2(co2 = co2Response.value)
                        fragment.updateHum(hum = humResponse.value)
                    }

                } catch (e: Exception) {
                    Log.e("!!!!!!!!!!", "Error fetching number: ${e.message} ${e.stackTrace}")

                }

                delay(60000)
            }
        }
    }

    fun cancelFetch() {
        job?.cancel()
    }
}