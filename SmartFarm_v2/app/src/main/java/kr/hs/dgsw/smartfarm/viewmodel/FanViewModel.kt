package kr.hs.dgsw.smartfarm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.network.model.response.Fan
import kr.hs.dgsw.smartfarm.network.model.response.Led
import kr.hs.dgsw.smartfarm.repository.FanRepository
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent

class FanViewModel: BaseViewModel() {
    val repository = FanRepository()
    val backBtn = SingleLiveEvent<Any>()
    val onOffBtn = SingleLiveEvent<Any>()

    val fanStatus = MutableLiveData<Boolean>()
    val postEvent = MutableLiveData<Boolean>()

    init {
        getFanValue()
    }

    fun getFanValue() {
        addDisposable(repository.getFan(), object : DisposableSingleObserver<Fan>() {
            override fun onSuccess(t: Fan) {
                fanStatus.value = t.status
            }

            override fun onError(e: Throwable) {
                onErrorEvent.value = e
            }
        })
    }

    fun postFanOnOff(params: HashMap<String?, Boolean?>) {
        addDisposable(repository.controlFan(params), object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                postEvent.value = t
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                onErrorEvent.value = e
            }

        })
    }


    fun onClickBackBtn(){
        backBtn.call()
    }

    fun onClickOnOffBtn(){
        onOffBtn.call()
    }
}