package kr.hs.dgsw.smartfarm.network.model.response


import com.google.gson.annotations.SerializedName

data class Co2(
    val status: Int,
    val value: Double
)