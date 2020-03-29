package com.eroom.erooja.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

@BindingAdapter("selectListener")
fun setNavigationChangeListener(navigation: BottomNavigationView, listener: BottomNavigationView.OnNavigationItemSelectedListener?) {
    listener?.let {
        navigation.setOnNavigationItemSelectedListener(it)
    }
}