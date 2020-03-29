package com.eroom.erooja.feature.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityTabBinding
import com.eroom.erooja.feature.main.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class TabActivity : AppCompatActivity(), TabContract.View {
    private lateinit var mainBinding: ActivityTabBinding
    private lateinit var presenter: TabPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
    }

    private fun initPresenter() {
        presenter = TabPresenter(this)
    }

    private fun setUpDataBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_tab)
        mainBinding.activity = this
    }

    private fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance()).commit()
    }

    val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_tab_feed -> {
                this.toastShort("메인")
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_category -> {
                this.toastShort("탐색 ^^")
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_my_review -> {
                this.toastShort("내 페이지야!!")
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

}
