package com.eroom.erooja.app.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.eroom.erooja.databinding.ItemMyParticipantedAddBinding

@SuppressLint("ViewConstructor")
class AddGoalItem(context: Context?, isClicked: () -> Unit) : ConstraintLayout(context) {
    init {
        initView(context, isClicked)
    }

    fun initView(context: Context?, isClicked: () -> Unit) {
        val binding =
            ItemMyParticipantedAddBinding.inflate(LayoutInflater.from(context), this, true)

        binding.back.setOnClickListener {
            isClicked()
        }
    }
}