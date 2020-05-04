package com.eroom.erooja.feature.setting.setting_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityChangeProfileSettingBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_profile_setting)
        binding.profileSetting = this@ProfileActivity
    }

    fun back(v: View){
        finish()
    }
}