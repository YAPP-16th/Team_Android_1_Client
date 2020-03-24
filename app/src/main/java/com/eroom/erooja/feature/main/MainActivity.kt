package com.eroom.erooja.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eroom.erooja.R

class MainActivity : AppCompatActivity(), MainContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
