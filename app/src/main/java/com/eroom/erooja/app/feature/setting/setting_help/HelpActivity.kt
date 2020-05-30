package com.eroom.erooja.app.feature.setting.setting_help

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityHelpSettingBinding

class HelpActivity : AppCompatActivity(), HelpContract.View {
    private lateinit var binding: ActivityHelpSettingBinding
    private lateinit var presenter: HelpPresenter

    private lateinit var question: Array<String>
    private lateinit var answer: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpDataBinding()
        initView()
    }

    private fun setUpDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_help_setting)
        binding.help = this@HelpActivity
    }

    private fun initView() {
        question = resources.getStringArray(R.array.help_question)
        answer = resources.getStringArray(R.array.help_answer)

        presenter = HelpPresenter(this)
        presenter.getArrayList(question, answer)

    }

    override fun setRecycler(question: Array<String>, answer: Array<String>) {
        binding.helpRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = HelpAdapter(context, question, answer)
        }
    }

    fun back(v: View) {
        finish()
    }
}

