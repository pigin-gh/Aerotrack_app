package ru.pigindev.aerozor.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.pigindev.aerozor.ui.home.HomeFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class DataFetcher(private val fragment: HomeFragment) {

    private var job: Job? = null

    val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://77.232.138.28:8000/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    init {
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
                        fragment.updateCO2(
                            co2 = co2Response.value
                        )
                        fragment.updateHum(
                            hum = humResponse.value
                        )
                        fragment.updateTemp(
                            temp = tempResponse.value
                        )
                    }

                } catch (e: Exception) {
                    Log.e("!!!!!!!!!!", "Error fetching number: ${e.message}")
                }

                delay(60000)
            }
        }
    }

    fun cancelFetch() {
        job?.cancel()
    }
}