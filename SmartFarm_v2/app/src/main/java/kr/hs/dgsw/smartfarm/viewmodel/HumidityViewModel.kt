package kr.hs.dgsw.smartfarm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.network.model.response.HumidityGnd
import kr.hs.dgsw.smartfarm.repository.HumidityRepository
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent
import okhttp3.internal.addHeaderLenient

class HumidityViewModel: BaseViewModel() {

    val repository = HumidityRepository()

    val humidityValue = MutableLiveData<Int>()
    val humidityState = MutableLiveData<Int>()

    val backBtn = SingleLiveEvent<Any>()

    init {
        humidityState.value = -2
        humidityValue.value = 0
        getHumidityValue()
    }

    fun getHumidityValue(){
        addDisposable(repository.getHumidity(), object : DisposableSingleObserver<HumidityGnd>(){
            override fun onSuccess(t: HumidityGnd) {
                humidityState.value = t.status
                humidityValue.value = t.value
            }

            override fun onError(e: Throwable) {
                onErrorEvent.value = e
                humidityState.value = -3
            }

        })
    }

    fun onClickBackBtn(){
        backBtn.call()
    }
}