package ru.pigindev.aerozor.data

import com.google.gson.annotations.SerializedName

data class LastvalResponse(
    @SerializedName("dtime") val timeStamp: String,
    @SerializedName("prmval") val value: Double,

)
