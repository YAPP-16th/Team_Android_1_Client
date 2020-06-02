package com.eroom.data.entity

data class UserSimpleData(var index:Int, var name:String, var like:Int, var check1:String,
                          var check2:String, var check3:String){

    lateinit var tempdata: ArrayList<UserSimpleData>

    fun getUserSimpleData(): ArrayList<UserSimpleData> {
        tempdata = arrayListOf(
            UserSimpleData(0,"sehee", 40, "ㄸㄸㄸ", "ㅁㄴㅇㄹ", "ㄴㅇㄹㄴㅇ"),
            UserSimpleData(1,"somebody", 99, "im shefm", "asdfe", " asdfeasg"),
            UserSimpleData(2,"somebody", 99, "im shefm", "asdfe", " asdfeasg"),
            UserSimpleData(3,"somebody", 99, "im shefm", "asdfe", " asdfeasg")
        )

    return tempdata
    }

    fun getUserDetailList(index:Int) : UserSimpleData {
        return getUserSimpleData()[index]
    }
}