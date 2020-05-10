package com.eroom.domain.customview.listeners

import android.os.SystemClock
import android.view.View

abstract class OnSingleClickListener: View.OnClickListener {
    //중복클릭시간차이
    companion object {
        const val MIN_CLICK_INTERVAL: Long = 600
    }
    //마지막으로 클릭한 시간
    private var mLastClickTime: Long = 0

    abstract fun onSingleClick(view: View)

    override fun onClick(view: View) {
        //현재 클릭한 시간
        val currentClickTime = SystemClock.uptimeMillis()
        // 이전에 클릭한 시간과 현재시간의 차이
        val elapsedTime = currentClickTime - mLastClickTime
        //마지막클릭시간 업데이트
        mLastClickTime = currentClickTime
        //내가 정한 중복클릭시간 차이를 안넘었으면 클릭이벤트 발생못하게 return
        if(elapsedTime<=MIN_CLICK_INTERVAL) return
        //중복클릭시간 아니면 이벤트 발생
        onSingleClick(view)
    }
}
