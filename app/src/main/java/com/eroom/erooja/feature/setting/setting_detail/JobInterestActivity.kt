package com.eroom.erooja.feature.setting.setting_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityFilterBinding

class JobInterestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        initView()
    }


    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        binding.setting = this@JobInterestActivity
    }

    private fun initView() {

    }
}