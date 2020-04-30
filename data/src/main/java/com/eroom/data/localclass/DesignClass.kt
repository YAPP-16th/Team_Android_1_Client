package com.eroom.data.localclass

enum class DesignClass(private val itemName: String) {
    UX("UX 디자인"),
    UIGUI("UI, GUI 디자인"),
    VIDEOMOTION("영상, 모션 디자인"),
    EDIT("편집 디자인"),
    BX("BX 디자인"),
    WEB("웹 디자인"),
    MOBILE("모바일 디자인"),
    GRAPIC("그래픽 디자인"),
    PACKAGE("제품 디자인");

    fun getName() = itemName

    companion object {
        fun getArray() = arrayListOf(
            UX, UIGUI, VIDEOMOTION, EDIT, BX, WEB, MOBILE, GRAPIC, PACKAGE
        )
    }
}