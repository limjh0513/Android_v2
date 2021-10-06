package kr.hs.dgsw.smartfarm.network.model.response


import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("status")
    val status: Int,
    @SerializedName("value")
    val value: Int
)