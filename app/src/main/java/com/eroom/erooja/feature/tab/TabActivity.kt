package com.eroom.erooja.feature.tab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.eroom.domain.utils.toastShort
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ActivityTabBinding
import com.eroom.erooja.feature.main.MainFragment
import com.eroom.erooja.feature.mypage.MyPageFragment
import com.eroom.erooja.feature.search.search_main.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class TabActivity : AppCompatActivity(), TabContract.View {
    private lateinit var mainBinding: ActivityTabBinding
    private lateinit var presenter: TabPresenter

    private var fragments: ArrayList<Fragment> = ArrayList()

    val FINISH_INTERVAL_TIME: Long = 2000
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initFragment()
    }

    private fun initPresenter() {
        presenter = TabPresenter(this)
    }

    private fun setUpDataBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_tab)
        mainBinding.activity = this
    }

    private fun initFragment() =
        fragments.apply {
            addAll(listOf(
                MainFragment.newInstance(),
               SearchFragment.newInstance(),
                MyPageFragment.newInstance()
            ))
        }.map {
            it.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_container, it).commit()
            }
        }.run {
            loadFragment(0)
        }


    private fun loadFragment(index: Int) =
        fragments.map {
            it.apply { supportFragmentManager.beginTransaction().hide(this).commit() }
        }[index].also {
            supportFragmentManager.beginTransaction().show(it).commit()
        }


    val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_tab_main -> {
                loadFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_search -> {
                loadFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_my_page -> {
                loadFragment(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }

    fun changeTabToSearch() {
        mainBinding.mainBottomTab.selectedItemId = R.id.bottom_tab_search
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime

        if (intervalTime in 0..FINISH_INTERVAL_TIME) {
            super.onBackPressed()
        }
        else {
            backPressedTime = tempTime
            this.toastShort(resources.getString(R.string.back_button_click))
        }
    }
}
