package kr.hs.dgsw.smartfarm.view.activity.details

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm.R
import kr.hs.dgsw.smartfarm.databinding.ActivityLedDetailsBinding
import kr.hs.dgsw.smartfarm.viewmodel.LedDetailsViewModel

class LedDetailsActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityLedDetailsBinding
    lateinit var mViewModel: LedDetailsViewModel
    lateinit var spannable: SpannableStringBuilder

    private val pickerValues = arrayOf("off", "on")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_led_details)
        mViewModel = ViewModelProvider(this).get(LedDetailsViewModel::class.java)

        mBinding.activity = this
        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        mBinding.LedImage.bringToFront() // 사진 앞으로 내보내기

        // numberPicker setting
        mBinding.ledonoffpicker.minValue = 0
        mBinding.ledonoffpicker.maxValue = pickerValues.size - 1
        mBinding.ledonoffpicker.displayedValues = pickerValues

        observerViewMode()
    }

    private fun observerViewMode() {
        initTextViewFail()

        with(mViewModel) {
            backBtn.observe(this@LedDetailsActivity, Observer {
                finish()
            })

            getErrorEvent.observe(this@LedDetailsActivity, Observer {
                Toast.makeText(this@LedDetailsActivity, "값을 전달받지 못했습니다.", Toast.LENGTH_SHORT).show()
                initTextViewFail()
            })

            postErrorEvent.observe(this@LedDetailsActivity, Observer {
                Toast.makeText(this@LedDetailsActivity, "값을 전송하지 못했습니다.", Toast.LENGTH_SHORT).show()
            })

            postEvent.observe(this@LedDetailsActivity, Observer {
                Toast.makeText(this@LedDetailsActivity, "값 전송을 성공했습니다.", Toast.LENGTH_SHORT).show()
            })

            ledStatus.observe(this@LedDetailsActivity, Observer {
                setStateImg(it)
                initTextViewSuccess(it)
            })

            onOffBtn.observe(this@LedDetailsActivity, Observer {
                onOffBtnOnclick()
            })
        }
    }

    private fun setStateImg(ledValue: Boolean) {
        if (ledValue) {
            mBinding.ledStateImg.setImageResource(R.drawable.led_state_img)
            mBinding.ledonoffpicker.value = 1
        } else {
            mBinding.ledStateImg.setImageResource(R.drawable.ledoff)
            mBinding.ledonoffpicker.value = 0
        }
    }

    private fun initTextViewFail() {
        val str: String = "값을 전달받지 못했습니다"
        spannable = SpannableStringBuilder(str)
        spannable.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0, 7,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            0, 7,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
    }

    fun onOffBtnOnclick() { // numberPicker 버튼 클릭 시
        var statusValue: Boolean = mBinding.ledonoffpicker.value != 0
        val params = HashMap<String?, Boolean?>()
        params["status"] = statusValue

        mViewModel.postLedOnOff(params)
    }

    private fun initTextViewSuccess(ledValue: Boolean) {
        if (!ledValue) {
            val str: String = "LED 기능이 꺼져있어요"
            spannable = SpannableStringBuilder(str)
            spannable.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, 7,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                0, 7,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        } else if (ledValue) {
            val str: String = "LED 기능이 켜져있어요"
            spannable = SpannableStringBuilder(str)
            spannable.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, 7,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                0, 7,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }
}