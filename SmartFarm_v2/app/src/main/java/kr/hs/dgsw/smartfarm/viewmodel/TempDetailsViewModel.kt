package kr.hs.dgsw.smartfarm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.smartfarm.base.BaseViewModel
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartfarm.network.model.response.Temp
import kr.hs.dgsw.smartfarm.repository.TempRepository
import kr.hs.dgsw.smartfarm.util.SingleLiveEvent

class TempDetailsViewModel: BaseViewModel() {
    val repository = TempRepository()

    val backBtn = SingleLiveEvent<Any>()

    val errorEvent = MutableLiveData<Throwable>()
    val tempStatus = MutableLiveData<Int>()
    val tempValue = MutableLiveData<Int>()

    init {
        tempValue.value = 0
        tempValue.value = -2
        getTempValue()
    }

    private fun getTempValue() {
        addDisposable(repository.getTemp(), object: DisposableSingleObserver<Temp>(){
            override fun onSuccess(t: Temp) {
                tempStatus.value = t.status
                tempValue.value = t.value
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                errorEvent.value = e
            }
        })
    }

    fun onClickBackBtn(){
        backBtn.call()
    }
}