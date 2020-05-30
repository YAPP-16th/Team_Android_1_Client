package com.eroom.data.entity

import com.google.gson.annotations.SerializedName

data class MinimalGoalDetailContent(
    @SerializedName("goalId") var goalId: Long,
    @SerializedName("role") var role: String,
    @SerializedName("isEnd") var isEnd: Boolean,
    @SerializedName("copyCount") var copyCount: Int,
    @SerializedName("startDt") var startDt: String,
    @SerializedName("endDt") var endDt: String,
    @SerializedName("minimalGoalDetail") var minimalGoalDetail: MinimalGoalDetail,
    @SerializedName("checkedTodoRate") var checkedTodoRate: Double
)
{
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass) {
            return false
        }

        other as MinimalGoalDetailContent

        if(goalId != other.goalId) {
            return false
        }
        if(role != other.role) {
            return false
        }
        if(isEnd != other.isEnd) {
            return false
        }
        if(copyCount != other.copyCount) {
            return false
        }
        if(startDt != other.startDt) {
            return false
        }
        if(endDt != other.endDt) {
            return false
        }
        if(minimalGoalDetail != other.minimalGoalDetail) {
            return false
        }
        return true
    }

}