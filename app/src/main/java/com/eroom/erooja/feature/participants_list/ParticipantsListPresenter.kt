package com.eroom.erooja.feature.participants_list

import androidx.recyclerview.widget.DiffUtil
import com.eroom.data.entity.Participant

class ParticipantsListPresenter(override val view: ParticipantsListContract.View) : ParticipantsListContract.Presenter {

    val mParticipantDiffCallback = object : DiffUtil.ItemCallback<Participant>() {
        override fun areItemsTheSame(oldItem: Participant, newItem: Participant): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Participant, newItem: Participant): Boolean {
            return (oldItem.name == newItem.name) && (oldItem.id == newItem.id)
        }
    }
}