package kr.hs.dgsw.smartfarm.view.activity.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm.R
import kr.hs.dgsw.smartfarm.databinding.ActivityCo2Binding
import kr.hs.dgsw.smartfarm.viewmodel.Co2ViewModel

class Co2Activity : AppCompatActivity() {
    lateinit var mBinding: ActivityCo2Binding
    lateinit var mViewModel: Co2ViewModel
    lateinit var spannable: SpannableStringBuilder


    private val pickerValues = arrayOf("off", "on")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_co2)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_co2)
        mViewModel = ViewModelProvider(this).get(Co2ViewModel::class.java)

        observerViewMode()
    }

    private fun observerViewMode() {
        with(mViewModel){
            backBtn.observe(this@Co2Activity, Observer {
                finish()
            })

            co2Value.observe(this@Co2Activity, Observer {
                mBinding.co2Co2Value.text = it
            })

            onErrorEvent.observe(this@Co2Activity, Observer {
                Toast.makeText(this@Co2Activity, "값을 전달받지 못했습니다.", Toast.LENGTH_SHORT).show()
            })
        }
    }
}