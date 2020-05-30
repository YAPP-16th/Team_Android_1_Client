package com.eroom.erooja.feature.setting.setting_detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityTosSettingBinding
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class TOSActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTosSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tos_setting)
        binding.tos = this@TOSActivity
    }

    private fun initView() {
        val inputStream: InputStream? = resources.openRawResource(R.raw.erooja_tos)

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
            binding.tosTxt.text = sb.toString()
        }

    }

    fun back(v: View) {
        finish()
    }
}