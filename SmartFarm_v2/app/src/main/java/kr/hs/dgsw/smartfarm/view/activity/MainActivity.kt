package kr.hs.dgsw.smartfarm.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.smartfarm.R
import kr.hs.dgsw.smartfarm.databinding.ActivityMainBinding
import kr.hs.dgsw.smartfarm.view.activity.details.*
import kr.hs.dgsw.smartfarm.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        observerViewModel();

        mBinding.refreshLayout.setOnRefreshListener {
            mViewModel.getSensorState()
            mBinding.refreshLayout.isRefreshing = false
        }
    }

    private fun observerViewModel() {
        with(mViewModel) {
            moistureStateView.observe(this@MainActivity, Observer {
                val intent = Intent(this@MainActivity, MoistureDetailsActivity::class.java)
                startActivity(intent)
            })

            tempStateView.observe(this@MainActivity, Observer {
                val intent = Intent(this@MainActivity, TempDetailsActivity::class.java)
                startActivity(intent)
            })

            ledStateView.observe(this@MainActivity, Observer {
                val intent = Intent(this@MainActivity, LedDetailsActivity::class.java)
                startActivity(intent)
            })

            fanStateView.observe(this@MainActivity, Observer {
                val intent = Intent(this@MainActivity, FanActivity::class.java)
                startActivity(intent)
            })

            co2StateView.observe(this@MainActivity, Observer {
                val intent = Intent(this@MainActivity, Co2Activity::class.java)
                startActivity(intent)
            })

            humidityStateView.observe(this@MainActivity, Observer {
                val intent = Intent(this@MainActivity, HumidityActivity::class.java)
                startActivity(intent)
            })

            errorEvent.observe(this@MainActivity, Observer {
                Toast.makeText(this@MainActivity, "서버와 연결을 실패했습니다.", Toast.LENGTH_SHORT).show()
            })

        }
    }
}