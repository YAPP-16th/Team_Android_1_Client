package com.eroom.erooja.feature.tab

import com.eroom.erooja.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class TabPresenter(override val view: TabContract.View) : TabContract.Presenter {
    override val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_tab_main -> {
                view.loadFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_search -> {
                view.loadFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_tab_my_page -> {
                view.loadFragment(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }
}