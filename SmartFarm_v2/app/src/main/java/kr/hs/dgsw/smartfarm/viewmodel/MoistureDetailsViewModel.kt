package kr.hs.dgsw.smartfarm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.network.model.response.Humidity
import kr.hs.dgsw.smartfarm.repository.MoistureRepository
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent

class MoistureDetailsViewModel : BaseViewModel() {

    val getErrorEvent = MutableLiveData<Throwable>()

    val repository = MoistureRepository()
    val backBtn = SingleLiveEvent<Any>()

    val moistureStatus = MutableLiveData<Int>()
    val moistureValue = MutableLiveData<Int>()
    init {
        moistureStatus.value = -2
        moistureValue.value = 0
        getMoistureValue()
    }

    fun onClickBackBtn() {
        backBtn.call()
    }

    fun getMoistureValue() {
        addDisposable(repository.getHumidity(),
            object : DisposableSingleObserver<Humidity>() {
                override fun onSuccess(t: Humidity) {

                    moistureStatus.value = t.status
                    moistureValue.value = t.value
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    getErrorEvent.value = e
                }
            })
    }
}