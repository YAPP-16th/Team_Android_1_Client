package com.eroom.erooja.feature.tab

import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

interface TabContract {
    interface View{
        fun loadFragment(index: Int): Fragment
    }

    interface Presenter {
        val view: View
        val listener: BottomNavigationView.OnNavigationItemSelectedListener
    }
}