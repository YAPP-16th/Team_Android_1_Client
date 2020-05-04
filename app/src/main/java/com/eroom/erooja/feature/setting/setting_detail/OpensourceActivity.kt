package com.eroom.erooja.feature.setting.setting_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityLicenceSettingBinding

class OpensourceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLicenceSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_licence_setting)
        binding.licence = this@OpensourceActivity
    }

    fun back(v: View){
        finish()
    }
}