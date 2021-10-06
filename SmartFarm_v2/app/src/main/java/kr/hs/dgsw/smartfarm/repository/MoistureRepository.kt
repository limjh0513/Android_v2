package kr.hs.dgsw.smartfarm.repository

import io.reactivex.Single
import kr.hs.dgsw.smartfarm.network.Server
import kr.hs.dgsw.smartfarm.network.model.response.HumidityGnd
import org.json.JSONObject

class MoistureRepository {
    fun getHumidityGnd(): Single<HumidityGnd> {
        return Server.retrofit.getHumidityGnd().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }

    fun controlWater(params: HashMap<String?, Boolean?>): Single<Void> {
        return Server.retrofit.postControlWater(params).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}