package com.eroom.erooja.feature.setting.setting_profile

interface ProfileContract {
    interface View{
        fun setProfileImage(imagePath: String?)
    }

    interface Presenter{
        var view: View
        fun getProfileImage()
        fun updateProfileImage(string: String)
    }
}