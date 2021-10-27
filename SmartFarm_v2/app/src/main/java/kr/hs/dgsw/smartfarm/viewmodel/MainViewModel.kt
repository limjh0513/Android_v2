package kr.hs.dgsw.smartfarm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent
import kr.hs.dgsw.smartfarm.network.model.response.getAll
import kr.hs.dgsw.smartfarm.repository.MainRepository

class MainViewModel : BaseViewModel() {
    private val repository = MainRepository()

    val errorEvent = MutableLiveData<Throwable>()

    val moistureSensorText = MutableLiveData<String>()
    val tempSensorText = MutableLiveData<String>()
    val ledSensorText = MutableLiveData<String>()
    val fanSensorText = MutableLiveData<String>()
    val co2SensorText = MutableLiveData<String>()
    val humiditySensorText = MutableLiveData<String>()

    val moistureStateView = SingleLiveEvent<Any>()
    val tempStateView = SingleLiveEvent<Any>()
    val ledStateView = SingleLiveEvent<Any>()
    val fanStateView = SingleLiveEvent<Any>()
    val co2StateView = SingleLiveEvent<Any>()
    val humidityStateView = SingleLiveEvent<Any>()

    init {
        moistureSensorText.value = "로딩중"
        tempSensorText.value = "로딩중"
        ledSensorText.value = "로딩중"
        fanSensorText.value = "로딩중"
        co2SensorText.value = "로딩중"
        humiditySensorText.value = "로딩중"

        getSensorState()
    }

    fun getSensorState() {

        addDisposable(repository.getAllSensorValue(), object : DisposableSingleObserver<getAll>() {
            override fun onSuccess(t: getAll) {
                val co2Value = Math.round(t.co2.value * 100) / 100.0

                moistureSensorText.value = t.humidity.value.toString() + "%"
                tempSensorText.value = t.temp.value.toString() + "도"
                ledSensorText.value = "더보기"
                fanSensorText.value = "더보기"
                co2SensorText.value = "${co2Value}ppm"
                humiditySensorText.value = t.humidityGnd.value.toString() + "%"

            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                errorEvent.value = e
            }

        })
    }

    fun onClickMoistureStateView() {
        moistureStateView.call()
    }

    fun onClickTempStateView() {
        tempStateView.call()
    }

    fun onClickLedStateView() {
        ledStateView.call()
    }

    fun onClickFanStateView() {
        fanStateView.call()
    }

    fun onClickCo2StateView() {
        co2StateView.call()
    }

    fun onClickHumidityStateView() {
        humidityStateView.call()
    }


}