package com.eroom.erooja.feature.mypage

import com.eroom.data.entity.JobClass

interface MyPageContract {
    interface View {
        fun setNickname(nickname: String)
        fun setJobInterestInfo(classList: ArrayList<JobClass>)
    }

    interface Presenter {
        val view: View
        fun getUserInfo()
        fun getMemberJobInterest()
        fun onCleared()
    }
}