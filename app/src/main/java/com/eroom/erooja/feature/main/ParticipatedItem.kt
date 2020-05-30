package com.eroom.erooja.feature.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.eroom.erooja.R
import com.eroom.erooja.databinding.ItemMyParticipantedListBinding

@SuppressLint("ViewConstructor")
class ParticipatedItem(
    context: Context?,
    goalId: Long,
    isOrg: Boolean,
    percent: String,
    jobClasses: String,
    titleText: String,
    duration: String,
    isClicked: (Long) -> Unit
) : LinearLayout(context) {
    init {
        initView(context, goalId, isOrg, percent, jobClasses, titleText, duration, isClicked)
    }

    fun initView(
        context: Context?,
        goalId: Long,
        isOrg: Boolean,
        percent: String,
        jobClasses: String,
        titleText: String,
        duration: String,
        isClicked: (Long) -> Unit
    ) {
        val binding =
            ItemMyParticipantedListBinding.inflate(LayoutInflater.from(context), this, true)

        context?.let {
            binding.back.background = if (isOrg)
                it.resources.getDrawable(R.drawable.back_item_list_org, null)
            else
                it.resources.getDrawable(R.drawable.back_item_list_grey, null)
        }

        binding.back.setOnClickListener { isClicked(goalId) }
        binding.percent.text = percent
        binding.jobClasses.text = jobClasses
        binding.titleText.text = titleText
        binding.duration.text = duration
    }
}