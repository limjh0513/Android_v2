package kr.hs.dgsw.smartfarm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent
import kr.hs.dgsw.smartfarm.network.model.response.getAll
import kr.hs.dgsw.smartfarm.repository.MainRepository

class MainViewModel: BaseViewModel() {
    private val repository = MainRepository()

    val errorEvent = MutableLiveData<Throwable>()

    val moistureSensorText = MutableLiveData<String>()
    val tempSensorText = MutableLiveData<String>()
    val ledSensorText = MutableLiveData<String>()

    val moistureStateView = SingleLiveEvent<Any>()
    val tempStateView = SingleLiveEvent<Any>()
    val ledStateView = SingleLiveEvent<Any>()
    val soilStateView = SingleLiveEvent<Any>()

    init {
        moistureSensorText.value = "로딩중"
        tempSensorText.value = "로딩중"
        ledSensorText.value = "로딩중"

        getSensorState()
    }

    fun getSensorState() {
        Log.e("123", "12")

        addDisposable(repository.getAllSensorValue(), object : DisposableSingleObserver<getAll>(){
            override fun onSuccess(t: getAll) {
                moistureSensorText.value = t.humidityGnd.value.toString()
                tempSensorText.value = t.temp.value.toString()
                ledSensorText.value = t.led.time.toString()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                errorEvent.value = e
            }

        })
    }

    fun onClickMoistureStateView(){
        moistureStateView.call()
    }

    fun onClickTempStateView(){
        tempStateView.call()
    }

    fun onClickLedStateView(){
        ledStateView.call()
    }

    fun onClickSoilStateView(){
        soilStateView.call()
    }


}