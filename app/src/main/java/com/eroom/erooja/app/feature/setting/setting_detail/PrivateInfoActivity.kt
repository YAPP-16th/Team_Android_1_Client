package com.eroom.erooja.app.feature.setting.setting_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityPrivateInfoBinding
import java.io.InputStreamReader

class PrivateInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrivateInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_private_info)
        binding.privateInfo = this@PrivateInfoActivity
    }

    private fun initView() {
        val data = resources.openRawResource(R.raw.private_info).use {
            val ir = InputStreamReader(it, "UTF-8")
            ir.readLines()
        }

        binding.contentText.text = data.joinToString("\r\n")
    }

    fun back() {
        finish()
    }
}

