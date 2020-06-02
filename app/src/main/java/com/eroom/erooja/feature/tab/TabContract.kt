package com.eroom.erooja.feature.tab

import com.google.android.material.bottomnavigation.BottomNavigationView

interface TabContract {
    interface View {
        fun loadFragment(index: Int): Int
        fun setUserInfo(uid: String, nickname: String, imagePath: String?)
    }

    interface Presenter {
        val view: View
        val listener: BottomNavigationView.OnNavigationItemSelectedListener
        fun onCleared()
        fun getUserInfo()
    }
}