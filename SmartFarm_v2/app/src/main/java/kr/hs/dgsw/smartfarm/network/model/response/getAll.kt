package kr.hs.dgsw.smartfarm.network.model.response


import com.google.gson.annotations.SerializedName

data class getAll(
    val co2: Co2,
    val humidity: Humidity,
    @SerializedName("humidity_gnd")
    val humidityGnd: HumidityGnd,
    val led: Led,
    val temp: Temp
)