package kr.hs.dgsw.smartfarm.view.activity.details

import android.animation.ValueAnimator
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
import kr.hs.dgsw.smartfarm.databinding.ActivityMoistureDetailsBinding
import kr.hs.dgsw.smartfarm.viewmodel.MoistureDetailsViewModel

class MoistureDetailsActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMoistureDetailsBinding
    lateinit var mViewModel: MoistureDetailsViewModel

    lateinit var spannable1: SpannableStringBuilder
    lateinit var spannable2: SpannableStringBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_moisture_details)
        mViewModel = ViewModelProvider(this).get(MoistureDetailsViewModel::class.java)

        mBinding.activity = this
        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        mBinding.MoistureImage.bringToFront() // 사진 앞으로 가져오기

        observerViewModel()
    }

    private fun observerViewModel() {
        initTextViewFail()
        setCircleProgress(mViewModel.moistureValue.value!!)

        with(mViewModel) {
            backBtn.observe(this@MoistureDetailsActivity, Observer {
                finish()
            })

            moistureStatus.observe(this@MoistureDetailsActivity, Observer {
                initTextViewSuccess(it)
            })

            moistureValue.observe(this@MoistureDetailsActivity, Observer {
                setCircleProgress(it)
            })

            getErrorEvent.observe(this@MoistureDetailsActivity, Observer {
                Toast.makeText(this@MoistureDetailsActivity, "값을 전달 받지 못했습니다.", Toast.LENGTH_SHORT)
                    .show()
                initTextViewFail()
                setCircleProgress(mViewModel.moistureValue.value!!)
            })
        }
    }

    private fun setCircleProgress(waterValue: Int) {
        if (waterValue != -2) {
            val animator = ValueAnimator.ofInt(0, waterValue)
            animator.addUpdateListener {
                val progress = animator.animatedValue as Int
                mBinding.waterCirclebar.progress = progress
            }
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

        mBinding.waterText1.text = spannable1
        mBinding.waterText2.text = spannable2
    }

    private fun initTextViewSuccess(moistureState: Int) { // 설명 text 굵기 조절
        if (moistureState == -1) {
            spannable1 = SpannableStringBuilder("수분이 부족해요")
            spannable1.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                2, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable1.setSpan(
                StyleSpan(Typeface.BOLD),
                0, // start
                2, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )

            spannable2 = SpannableStringBuilder("물주기 기능을 이용해 물을 주세요")
            spannable2.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                6, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable2.setSpan(
                StyleSpan(Typeface.BOLD),
                0, // start
                6, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        } else if (moistureState == 0) {
            spannable1 = SpannableStringBuilder("수분이 적당해요")
            spannable1.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                2, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable1.setSpan(
                StyleSpan(Typeface.BOLD),
                0, // start
                2, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )

            spannable2 = SpannableStringBuilder("수분이 아주 완벽한 상태에요")
            spannable2.setSpan(
                ForegroundColorSpan(Color.BLACK),
                3, // start
                10, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable2.setSpan(
                StyleSpan(Typeface.BOLD),
                3, // start
                10, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        } else if (moistureState == 1) {
            spannable1 = SpannableStringBuilder("수분이 과해요")
            spannable1.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                3, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable1.setSpan(
                StyleSpan(Typeface.BOLD),
                0, // start
                3, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )

            spannable2 = SpannableStringBuilder("과유불급")
            spannable2.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0, // start
                4, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable2.setSpan(
                StyleSpan(Typeface.BOLD),
                0, // start
                4, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }

        mBinding.waterText1.text = spannable1
        mBinding.waterText2.text = spannable2
    }
}