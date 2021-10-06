package kr.hs.dgsw.smartfarm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.network.model.response.Led
import kr.hs.dgsw.smartfarm.repository.LedRepository
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent

class LedDetailsViewModel : BaseViewModel() {
    val repository = LedRepository()
    val getErrorEvent = MutableLiveData<Throwable>()

    val postErrorEvent = MutableLiveData<Throwable>()
    val postEvent = MutableLiveData<Void>()

    val backBtn = SingleLiveEvent<Any>()
    val onOffBtn = SingleLiveEvent<Any>()

    val ledStatus = MutableLiveData<Boolean>()

    init {
        getLedValue()
    }

    fun getLedValue() {
        addDisposable(repository.getLed(), object : DisposableSingleObserver<Led>() {
            override fun onSuccess(t: Led) {
                ledStatus.value = t.status
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                getErrorEvent.value = e
            }
        })
    }

    fun postLedOnOff(params: HashMap<String?, Boolean?>) {
        addDisposable(repository.controlLed(params), object : DisposableSingleObserver<Void>() {
            override fun onSuccess(t: Void) {
                postEvent.value = t
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                postErrorEvent.value = e
            }

        })
    }

    fun onClickBackBtn() {
        backBtn.call()
    }

    fun onClickOnOffBtn() {
        onOffBtn.call()
    }
}