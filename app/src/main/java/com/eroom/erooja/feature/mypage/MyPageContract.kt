package com.eroom.erooja.feature.mypage

interface MyPageContract {
    interface View {
        fun setNickname(nickname: String)
        fun setJobInterestInfo(randomJob: String, randomJobId: Long, size: Int)
    }

    interface Presenter {
        val view: View
        fun getUserInfo()
        fun getMemberJobInterest()
        fun onCleared()
    }
}