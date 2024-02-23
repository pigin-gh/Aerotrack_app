package ru.pigindev.aerozor.data

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

class DataFetcher(private val fragment: HomeFragment) {

    private var job: Job? = null

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://77.232.138.28:8000/lastval/")
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
                    val response = apiService.getECO2()
                    withContext(Dispatchers.Main) {
                        fragment.updateECO2Data(response.prmval)
                    }
                } catch (e: Exception) {
                    println("Error fetching number: ${e.message}")
                }
                delay(60000)
            }
        }
    }

    fun cancelFetch() {
        job?.cancel()
    }
}