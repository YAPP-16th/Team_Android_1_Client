package com.eroom.data.localclass

enum class DevelopClass(private val itemName: String) {
    SERVER("서버"),
    FRONTEND("프론트엔드"),
    ANDROID("안드로이드"),
    IOS("iOS"),
    DEVOPS("DevOps"),
    MACHINELEARNING("머신 러닝"),
    DATASCIENTIST("Data Scientist"),
    DATAENGINEER("Data Engineer"),
    GAMEANIMATION("게임, 애니메이션");

    fun getName() = itemName

    companion object {
        fun getArray() = arrayListOf(
            SERVER, FRONTEND, ANDROID, IOS, DEVOPS, MACHINELEARNING,
            DATASCIENTIST, DATAENGINEER, GAMEANIMATION)
    }
}