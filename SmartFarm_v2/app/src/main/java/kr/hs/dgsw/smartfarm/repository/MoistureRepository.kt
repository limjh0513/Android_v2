package kr.hs.dgsw.smartfarm.repository

import io.reactivex.Single
import kr.hs.dgsw.smartfarm.network.Server
import kr.hs.dgsw.smartfarm.network.model.response.Humidity
import kr.hs.dgsw.smartfarm.network.model.response.HumidityGnd
import org.json.JSONObject

class MoistureRepository {
    fun getHumidity(): Single<Humidity> {
        return Server.retrofit.getHumidity().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}