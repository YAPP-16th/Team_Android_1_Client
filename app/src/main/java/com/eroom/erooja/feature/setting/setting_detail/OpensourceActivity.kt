package com.eroom.erooja.feature.setting.setting_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityLicenceSettingBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class OpensourceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLicenceSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_licence_setting)
        binding.licence = this@OpensourceActivity
    }

    private fun initView() {
        val inputStream: InputStream? = resources.openRawResource(R.raw.open_source)

        inputStream?.let {
            val stream = InputStreamReader(inputStream, "UTF-8")
            val buffer = BufferedReader(stream)
            var read: String?
            val sb = StringBuilder("")

            while (true) {
                read = buffer.readLine()

                if (read == null) break
                else if (read.equals("")) sb.append("\n")
                else sb.append(read)
            }
            binding.contentText.text = sb.toString()
        }
    }

    fun back(v: View) {
        finish()
    }
}