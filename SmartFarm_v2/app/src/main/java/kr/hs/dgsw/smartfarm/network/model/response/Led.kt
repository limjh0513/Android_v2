package kr.hs.dgsw.smartfarm.network.model.response


import com.google.gson.annotations.SerializedName

data class Led(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("time")
    val time: Int
)