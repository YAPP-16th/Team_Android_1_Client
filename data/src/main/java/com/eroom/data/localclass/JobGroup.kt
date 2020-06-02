package com.eroom.data.localclass

enum class JobGroup(val groupName: String) {
    DEVELOP("개발"), DESIGN("디자인");

    companion object {
        fun getGroup() = arrayListOf(DEVELOP, DESIGN)
    }
}