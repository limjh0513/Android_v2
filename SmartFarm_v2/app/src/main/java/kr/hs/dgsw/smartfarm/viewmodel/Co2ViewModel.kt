package kr.hs.dgsw.smartfarm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.network.model.response.Co2
import kr.hs.dgsw.smartfarm.network.model.response.Led
import kr.hs.dgsw.smartfarm.repository.Co2Repository
import kr.hs.dgsw.smartfarm.repository.LedRepository
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent

class Co2ViewModel: BaseViewModel() {
    val repository = Co2Repository()
    val backBtn = SingleLiveEvent<Any>()

    val co2Value = MutableLiveData<String>()
    val errorEvent = MutableLiveData<Throwable>()

    init {
        co2Value.value = "로딩중"
        getLedValue()
    }

    fun getLedValue() {
        addDisposable(repository.getCo2(), object : DisposableSingleObserver<Co2>() {
            override fun onSuccess(t: Co2) {
                Log.e("hhhhhhh", "${t.value}")
                co2Value.value = "${(Math.round(t.value * 100) / 100)}ppm"
                Log.e("hhhhhhh", "${co2Value.value}")
            }

            override fun onError(e: Throwable) {
                onErrorEvent.value = e
            }
        })
    }

    fun onClickBackBtn(){
        backBtn.call()
    }
}