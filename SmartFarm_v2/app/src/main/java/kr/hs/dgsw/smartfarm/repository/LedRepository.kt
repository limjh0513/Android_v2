package kr.hs.dgsw.smartfarm.repository

import io.reactivex.Completable
import io.reactivex.Single
import kr.hs.dgsw.smartfarm.network.Server
import kr.hs.dgsw.smartfarm.network.model.response.Led
import org.json.JSONObject

class LedRepository {
    fun getLed(): Single<Led> {
        return Server.retrofit.getLed().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }

    fun controlLed(params: HashMap<String?, Boolean?>): Single<Boolean> {
        return Server.retrofit.postControlLed(params).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.isSuccessful
        }
    }
}