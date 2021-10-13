package kr.hs.dgsw.smartfarm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.network.model.response.Humidity
import kr.hs.dgsw.smartfarm.repository.MoistureRepository
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent

class MoistureDetailsViewModel : BaseViewModel() {

    val getErrorEvent = MutableLiveData<Throwable>()
    val postErrorEvent = MutableLiveData<Throwable>()
    val postEvent = MutableLiveData<Void>()

    val repository = MoistureRepository()
    val backBtn = SingleLiveEvent<Any>()

    val moistureStatus = MutableLiveData<Int>()
    val moistureValue = MutableLiveData<Int>()
    val onOffBtn = SingleLiveEvent<Any>()

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

    fun postControlWater(params: HashMap<String?, Boolean?>) {
        addDisposable(repository.controlWater(params), object : DisposableSingleObserver<Void>() {
            override fun onSuccess(t: Void) {
                postEvent.value = t
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                postErrorEvent.value = e
            }

        })

    }

    fun onClickOnOffBtn() {
        onOffBtn.call()
    }
}