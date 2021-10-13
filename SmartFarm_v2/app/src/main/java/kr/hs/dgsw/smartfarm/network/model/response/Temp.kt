package kr.hs.dgsw.smartfarm.network.model.response


import com.google.gson.annotations.SerializedName

data class Temp(
    val status: Int,
    val value: Int
)