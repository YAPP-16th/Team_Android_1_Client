package com.eroom.erooja.feature.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.eroom.data.entity.JobGroup
import com.eroom.data.response.JobGroupAndClassResponse
import com.google.android.material.bottomnavigation.BottomNavigationView

interface TabContract {
    interface View{
        fun loadFragment(index: Int): Int
        fun setUserInfo(uid: String, nickname: String, imagePath: String)
    }

    interface Presenter {
        val view: View
        val listener: BottomNavigationView.OnNavigationItemSelectedListener
        fun onCleared()
        fun getUserInfo()
    }
}