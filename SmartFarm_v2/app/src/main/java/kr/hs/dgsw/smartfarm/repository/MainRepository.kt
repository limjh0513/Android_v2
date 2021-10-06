package kr.hs.dgsw.smartfarm.repository

import android.util.Log
import io.reactivex.Single
import kr.hs.dgsw.smartfarm.network.Server
import kr.hs.dgsw.smartfarm.network.model.response.getAll
import org.json.JSONObject

class MainRepository {
    fun getAllSensorValue(): Single<getAll> {
        return Server.retrofit.getSensorAll().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            Log.e("eeree", it.body().toString())
            it.body()
        }
    }
}