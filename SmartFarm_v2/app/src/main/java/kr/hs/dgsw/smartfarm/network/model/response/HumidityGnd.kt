package kr.hs.dgsw.smartfarm.network.model.response


import com.google.gson.annotations.SerializedName

data class HumidityGnd(
    val status: Int,
    val value: Int
)