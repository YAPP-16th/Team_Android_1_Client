package com.eroom.erooja.feature.setting.setting_detail.setting_profile

interface ProfileContract {
    interface View{
        fun setProfileImage(imagePath: String?)
    }

    interface Presenter{
        var view: View
        fun getProfileImage()
    }
}