package kr.hs.dgsw.smartfarm.repository

import io.reactivex.Single
import kr.hs.dgsw.smartfarm.network.Server
import kr.hs.dgsw.smartfarm.network.model.response.Fan
import kr.hs.dgsw.smartfarm.network.model.response.Led
import org.json.JSONObject

class FanRepository {
    fun getFan(): Single<Fan> {
        return Server.retrofit.getFan().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }

    fun controlFan(params: HashMap<String?, Boolean?>): Single<Boolean> {
        return Server.retrofit.postControlFan(params).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.isSuccessful
        }
    }
}