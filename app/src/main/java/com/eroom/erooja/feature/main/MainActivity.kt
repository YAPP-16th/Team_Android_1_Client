package com.eroom.erooja.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.domain.api.usecase.GetDummyUseCase
import com.eroom.domain.sharedpref.SharedPreferenceHelper
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityMainBinding
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initPresenter()
    }

    private fun setUpDataBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.activity = this
    }

    private fun initPresenter() {
        presenter = MainPresenter(this, get())
        presenter.test()
    }
}
