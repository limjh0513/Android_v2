package kr.hs.dgsw.smartfarm.view.activity.details

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm.R
import kr.hs.dgsw.smartfarm.databinding.ActivityTempDetailsBinding
import kr.hs.dgsw.smartfarm.viewmodel.TempDetailsViewModel

class TempDetailsActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityTempDetailsBinding
    lateinit var mViewModel: TempDetailsViewModel

    lateinit var spannable1: SpannableStringBuilder
    lateinit var spannable2: SpannableStringBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_temp_details)
        mViewModel = ViewModelProvider(this).get(TempDetailsViewModel::class.java)

        mBinding.activity = this
        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        mBinding.tempImage.bringToFront()

        observerViewModel()
    }

    private fun observerViewModel() {
        initTextViewFail()
        setCircleProgress(mViewModel.tempValue.value!!)

        with(mViewModel) {
            backBtn.observe(this@TempDetailsActivity, Observer {
                finish()
            })

            errorEvent.observe(this@TempDetailsActivity, Observer {
                initTextViewFail()
                setCircleProgress(mViewModel.tempValue.value!!)
            })

            tempStatus.observe(this@TempDetailsActivity, Observer {
                initTextViewSuccess(it)
            })

            tempValue.observe(this@TempDetailsActivity, Observer {
                setCircleProgress(it)
            })
        }
    }

    private fun setCircleProgress(tempValue: Int) {
        if (tempValue == -2) {
            mBinding.tempText.text = "0도"
        } else {
            val animator = ValueAnimator.ofInt(0, tempValue)
            animator.addUpdateListener {
                val progress = animator.animatedValue as Int
                mBinding.tempCirclebar.progress = progress
            }

            mBinding.tempText.text = "${tempValue}도"
            animator.duration = 1500
            animator.start()
        }

    }

    private fun initTextViewFail() {
        spannable1 = SpannableStringBuilder("값을 전달받지")
        spannable1.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0, // start
            7, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannable1.setSpan(
            StyleSpan(Typeface.BOLD),
            0, // start
            7, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        spannable2 = SpannableStringBuilder("못했습니다.")

        mBinding.tempText1.text = spannable1
        mBinding.tempText2.text = spannable2

    }

    private fun initTextViewSuccess(tempState: Int) {
        if (tempState == -1) {
            val str1: String = "적당한 온도에요"
            val str2: String = "정말 기분이 좋은 날 입니다..."
            spannable1 = SpannableStringBuilder(str1)
            spannable1.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                6, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable1.setSpan(
                StyleSpan(Typeface.BOLD),
                0, // start
                6, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )

            spannable2 = SpannableStringBuilder(str2)
            spannable2.setSpan(
                ForegroundColorSpan(Color.BLACK),
                6, // start
                10, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable2.setSpan(
                StyleSpan(Typeface.BOLD),
                6, // start
                10, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        } else if (tempState == 0) {
            val str1: String = "온도가 낮아요"
            val str2: String = "정말 추운날 입니다..."
            spannable1 = SpannableStringBuilder(str1)
            spannable1.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                7, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable1.setSpan(
                StyleSpan(Typeface.BOLD),
                0, // start
                7, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )

            spannable2 = SpannableStringBuilder(str2)
            spannable2.setSpan(
                ForegroundColorSpan(Color.BLACK),
                2, // start
                6, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable2.setSpan(
                StyleSpan(Typeface.BOLD),
                2, // start
                6, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        } else if (tempState == 1) {
            val str1: String = "온도가 높아요"
            val str2: String = "정말 더운날 입니다..."
            spannable1 = SpannableStringBuilder(str1)
            spannable1.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                7, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable1.setSpan(
                StyleSpan(Typeface.BOLD),
                0, // start
                7, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )

            spannable2 = SpannableStringBuilder(str2)
            spannable2.setSpan(
                ForegroundColorSpan(Color.BLACK),
                2, // start
                6, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable2.setSpan(
                StyleSpan(Typeface.BOLD),
                2, // start
                6, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
        mBinding.tempText1.text = spannable1
        mBinding.tempText2.text = spannable2
    }
}