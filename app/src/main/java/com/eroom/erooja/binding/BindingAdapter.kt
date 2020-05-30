package com.eroom.erooja.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

@BindingAdapter("selectListener")
fun setNavigationChangeListener(navigation: BottomNavigationView, listener: BottomNavigationView.OnNavigationItemSelectedListener?) {
    listener?.let {
        navigation.setOnNavigationItemSelectedListener(it)
    }
}

@BindingAdapter("scrollAdapter")
fun setScrollAdapter(recyclerView: RecyclerView, listener: RecyclerView.OnScrollListener) {
    recyclerView.addOnScrollListener(listener)
}