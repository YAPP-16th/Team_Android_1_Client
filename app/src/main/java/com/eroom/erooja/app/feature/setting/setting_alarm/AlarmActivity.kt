package com.eroom.erooja.app.feature.setting.setting_alarm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityAlarmSettingBinding
import org.koin.android.ext.android.get
import timber.log.Timber

class AlarmActivity : AppCompatActivity(), AlarmContract.View {
    private lateinit var binding: ActivityAlarmSettingBinding
    private lateinit var presenter: AlarmPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        initPresenter()
        initView()
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm_setting)
        binding.alarm = this@AlarmActivity
    }

    private fun initPresenter() {
        presenter = AlarmPresenter(this, get())
    }

    private fun initView() {
        presenter.getAlarmFlag()

        binding.switch1.setOnCheckedChangeListener { _, _ ->
            presenter.setAlarmFlag(binding.switch1.isChecked)
            Timber.e("${binding.switch1.isChecked}")
        }
    }

    override fun setAlarmSwitch(switch: Boolean) {
        binding.switch1.isChecked = switch
        Timber.i("$switch")
    }

    override fun completeAlarmSetting() {
        Timber.i("Alarm Setting Flag Save Success")
    }

    fun back(v: View) {
        finish()
    }
}