package kr.hs.dgsw.smartfarm.repository

import io.reactivex.Single
import kr.hs.dgsw.smartfarm.network.Server
import kr.hs.dgsw.smartfarm.network.model.response.Co2
import kr.hs.dgsw.smartfarm.network.model.response.Led
import org.json.JSONObject

class Co2Repository {
    fun getCo2(): Single<Co2> {
        return Server.retrofit.getCo2().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}