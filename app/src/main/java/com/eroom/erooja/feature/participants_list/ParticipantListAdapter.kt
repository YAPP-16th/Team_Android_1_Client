package com.eroom.erooja.feature.participants_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.Participant
import com.eroom.erooja.databinding.ItemParticipantsBinding

class ParticipantListAdapter(callback : DiffUtil.ItemCallback<Participant>, private val list: ArrayList<Participant>) : ListAdapter<Participant, RecyclerView.ViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val binding = ItemParticipantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParticipantViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ParticipantViewHolder).bind(list[position].name)
    }
}

class ParticipantViewHolder(val binding: ItemParticipantsBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(name: String) {
        binding.profileNameText.text = name
    }
}