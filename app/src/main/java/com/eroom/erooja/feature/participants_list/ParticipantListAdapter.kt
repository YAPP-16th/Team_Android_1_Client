package com.eroom.erooja.feature.participants_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eroom.data.entity.Member
import com.eroom.domain.utils.add
import com.eroom.domain.utils.loadUrl
import com.eroom.erooja.databinding.ItemParticipantsBinding

class ParticipantListAdapter(
    callback: DiffUtil.ItemCallback<Member>,
    private val list: ArrayList<Member>,
    private val uId: String,
    private val clicked: (Member) -> Unit
) :
    ListAdapter<Member, ParticipantViewHolder>(callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val binding =
            ItemParticipantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParticipantViewHolder(
            binding,
            binding.profileNameText,
            binding.profileJobClassInfoText,
            binding.addInfoText,
            binding.itemAll,
            binding.profile,
            binding.naviToProfile
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        var a = list
        holder.name.text =
            list[position].nickname add if (list[position].uid == uId) "(you)" else ""
        holder.jobClassText.text =
            list[position].jobInterests[0].name add if (list[position].jobInterests.size < 2) "" else " 외 ${list[position].jobInterests.size - 1}개"
        if (list[position].uid == uId) holder.naviImage.visibility = View.GONE
        holder.item.setOnClickListener {
            if (list[position].uid != uId) {
                clicked(list[position])
            }
        }
        if (list[position].role == "OWNER")
            holder.infoText.visibility = View.VISIBLE
        holder.profile.loadUrl(list[position].imagePath)

    }
}

class ParticipantViewHolder(
    val binding: ItemParticipantsBinding,
    val name: TextView,
    val jobClassText: TextView,
    val infoText: TextView,
    val item: ConstraintLayout,
    val profile: ImageView,
    val naviImage: ImageView
) : RecyclerView.ViewHolder(binding.root)
