package kr.hs.dgsw.smartfarm.repository

import io.reactivex.Single
import kr.hs.dgsw.smartfarm.network.Server
import kr.hs.dgsw.smartfarm.network.api.SmartFarmService
import kr.hs.dgsw.smartfarm.network.model.response.*
import org.json.JSONObject

class TempRepository {

    fun getTemp(): Single<Temp>{
        return Server.retrofit.getTemp().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}