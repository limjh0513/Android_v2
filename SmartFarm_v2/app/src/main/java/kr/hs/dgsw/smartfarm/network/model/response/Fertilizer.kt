package kr.hs.dgsw.smartfarm.network.model.response


import com.google.gson.annotations.SerializedName

data class Fertilizer(
    @SerializedName("status")
    val status: Int
)