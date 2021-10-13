package kr.hs.dgsw.smartfarm.network.api

import io.reactivex.Single
import kr.hs.dgsw.smartfarm.network.model.response.*
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SmartFarmService {
    @GET("get_all_sensor")
    fun getSensorAll(): Single<Response<getAll>>

    @GET("humidity_gnd")
    fun getHumidityGnd(): Single<Response<HumidityGnd>>

    @GET("humidity")
    fun getHumidity(): Single<Response<Humidity>>

    @GET("temp")
    fun getTemp(): Single<Response<Temp>>

    @GET("air")
    fun getCo2(): Single<Response<Co2>>

    @GET("led")
    fun getLed(): Single<Response<Led>>

    @GET("fan")
    fun getFan(): Single<Response<Fan>>

    @FormUrlEncoded
    @POST("control_water")
    fun postControlWater(
        @FieldMap
        params: HashMap<String?, Boolean?>
    ): Single<Response<Void>>

    @FormUrlEncoded
    @POST("control_led")
    fun postControlLed(
        @FieldMap
        params: HashMap<String?, Boolean?>
    ): Single<Response<Void>>

    @FormUrlEncoded
    @POST("control_fan")
    fun postControlFan(
        @FieldMap
        params: HashMap<String?, Boolean?>
    ): Single<Response<Void>>
}