package com.eroom.erooja.feature.goalDetail

import com.eroom.data.entity.Participant
import com.eroom.data.entity.UserSimpleData

class GoalDetailPresenter(override var view: GoalDetailContract.View)
    :GoalDetailContract.Presenter{

    var tempdata: ArrayList<UserSimpleData> = arrayListOf(UserSimpleData("sehee", 40, 20,"ㄸㄸㄸ","ㅁㄴㅇㄹ","ㄴㅇㄹㄴㅇ"),
        UserSimpleData("somebody", 99, 22, "im shefm", "asdfe", " asdfeasg"),
        UserSimpleData("somebody", 99, 22, "im shefm", "asdfe", " asdfeasg"),
        UserSimpleData("somebody", 99, 22, "im shefm", "asdfe", " asdfeasg"))


    override fun getData() {
        view.getAllView(tempdata)

    }
}