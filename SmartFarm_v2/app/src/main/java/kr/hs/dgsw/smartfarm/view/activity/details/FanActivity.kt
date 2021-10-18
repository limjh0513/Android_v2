package kr.hs.dgsw.smartfarm.view.activity.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm.R
import kr.hs.dgsw.smartfarm.databinding.ActivityFanBinding
import kr.hs.dgsw.smartfarm.viewmodel.FanViewModel

class FanActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityFanBinding
    lateinit var mViewModel: FanViewModel

    lateinit var spannable: SpannableStringBuilder

    private val pickerValues = arrayOf("off", "on")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fan)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_fan)
        mViewModel = ViewModelProvider(this).get(FanViewModel::class.java)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this


        mBinding.fanPicker.minValue = 0
        mBinding.fanPicker.maxValue = pickerValues.size - 1
        mBinding.fanPicker.displayedValues = pickerValues

        observerViewModel()
    }

    private fun observerViewModel() {
        with(mViewModel) {
            backBtn.observe(this@FanActivity, Observer {
                finish()
            })

            onOffBtn.observe(this@FanActivity, Observer {
                var statusValue: Boolean = mBinding.fanPicker.value != 0
                val params = HashMap<String?, Boolean?>()
                params["status"] = statusValue

                mViewModel.postFanOnOff(params)
            })

            postEvent.observe(this@FanActivity, Observer {
                Toast.makeText(this@FanActivity, "성공적으로 전달했습니다.", Toast.LENGTH_SHORT).show()
                getFanValue()
            })

            fanStatus.observe(this@FanActivity, Observer {
                if(it){
                    mBinding.fanIcon.setImageResource(R.drawable.fan_on_icon)
                    mBinding.fanStateMessage.text = "환풍기 기능이 켜져있어요."
                } else {

                    mBinding.fanIcon.setImageResource(R.drawable.off_fan_ic)
                    mBinding.fanStateMessage.text = "환풍기 기능이 꺼져있어요."
                }
            })

            onErrorEvent.observe(this@FanActivity, Observer {
                Toast.makeText(this@FanActivity, "오류가 발생했습니다..", Toast.LENGTH_SHORT).show()
            })
        }
    }
}