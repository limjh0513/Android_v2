package kr.hs.dgsw.smartfarm.view.activity.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm.R
import kr.hs.dgsw.smartfarm.databinding.ActivityHumidityBinding
import kr.hs.dgsw.smartfarm.viewmodel.HumidityViewModel
import kr.hs.dgsw.smartfarm.viewmodel.LedDetailsViewModel

class HumidityActivity : AppCompatActivity() {
    lateinit var mViewModel: HumidityViewModel
    lateinit var mBinding: ActivityHumidityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_humidity)
        mViewModel = ViewModelProvider(this).get(HumidityViewModel::class.java)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        mBinding.soilTitleImg.bringToFront()

        observerViewModel()
    }

    private fun observerViewModel() {
        with(mViewModel){
            backBtn.observe(this@HumidityActivity, Observer {
                finish()
            })

            humidityValue.observe(this@HumidityActivity, Observer {
                mBinding.humidityProgressbar.progress = it
            })

            humidityState.observe(this@HumidityActivity , Observer {
                if(it == -1){
                    mBinding.tvCurrent.text = "토양수분이 부족해요."
                } else if(it == 0){
                    mBinding.tvCurrent.text = "토양수분이 적절해요."
                } else if(it == 1){
                    mBinding.tvCurrent.text = "토양수분이 과해요."
                } else if(it == -2){
                    mBinding.tvCurrent.text = "로딩중."
                } else {
                    mBinding.tvCurrent.text = "값을 전달받지 못했습니다."
                }
            })

            onErrorEvent.observe(this@HumidityActivity, Observer {
                Toast.makeText(this@HumidityActivity, "값을 전달받지 못했습니다.", Toast.LENGTH_SHORT).show()
            })
        }
    }
}