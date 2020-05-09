package com.eroom.data.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class MinimalGoalDetailContent(
    @JsonProperty("goalId") var goalId: Long,
    @JsonProperty("role") var role: String,
    @JsonProperty("isEnd") var isEnd: Boolean,
    @JsonProperty("copyCount") var copyCount: Int,
    @JsonProperty("startDt") var startDt: String,
    @JsonProperty("endDt") var endDt: String,
    @JsonProperty("minimalGoalDetail") var minimalGoalDetail: MinimalGoalDetail,
    @JsonProperty("checkedTodoRate") var checkedTodoRate: Double
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