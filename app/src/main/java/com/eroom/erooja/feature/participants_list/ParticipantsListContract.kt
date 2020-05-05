package com.eroom.erooja.feature.participants_list

import com.eroom.data.entity.Member

interface ParticipantsListContract {
    interface View {
        fun updateList(list: ArrayList<Member>, totalElement: Int)
        fun updateIsEnd(boolean: Boolean)
    }

    interface Presenter {
        val view: View
        fun getParticipantedList(goalId: Long, page: Int)
        fun onCleared()
    }
}