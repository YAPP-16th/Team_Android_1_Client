package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemMyParticipantedAddBinding
import com.eroom.erooja.databinding.ItemMyParticipantedListBinding

@SuppressLint("ViewConstructor")
class AddGoalItem(context: Context?, isClicked: () -> Unit) : ConstraintLayout(context) {
    init {
        initView(context, isClicked)
    }

    fun initView(context: Context?, isClicked: () -> Unit) {
        val binding = ItemMyParticipantedAddBinding.inflate(LayoutInflater.from(context), this, true)

        binding.back.setOnClickListener {
            isClicked()
        }
    }
}