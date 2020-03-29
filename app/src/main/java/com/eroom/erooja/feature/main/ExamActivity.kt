package com.eroom.erooja.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.domain.koin.repository.SharedPrefRepository
import com.eroom.domain.utils.*
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityExamBinding
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class ExamActivity : AppCompatActivity(), ExamContract.View {
    private lateinit var mainBinding: ActivityExamBinding
    private lateinit var presenter: ExamPresenter
    private val sharedPrefRepository: SharedPrefRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
    }

    private fun setUpDataBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_exam)
        mainBinding.activity = this
    }

    private fun initPresenter() {
        presenter = ExamPresenter(this, get(), sharedPrefRepository)
        presenter.testGet()
    }

    fun test1() {

    }

    fun testClick() {
        this.toastShort(listOf("1", "2", "3").join())
    }
}
