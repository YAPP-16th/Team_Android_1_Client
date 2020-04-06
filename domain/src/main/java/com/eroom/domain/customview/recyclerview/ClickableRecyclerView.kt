package com.eroom.domain.customview.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class ClickableRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private var clickTriggerRunnable: ClickTriggerRunnable? = null
    private val touchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop
    private var touchStartPointX = 0.0f
    private var touchStartPointY = 0.0f

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        e?.run {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    touchStartPointX = x
                    touchStartPointY = y

                    //클릭 이벤트 처리를 위한 runnable 실행
                    clickTriggerRunnable = ClickTriggerRunnable()
                    handler.postDelayed(clickTriggerRunnable, ViewConfiguration.getTapTimeout().toLong())
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = Math.abs(touchStartPointX - x)
                    val deltaY = Math.abs(touchStartPointY - y)

                    //클릭에 대한 터치 영역을 벗어날 경우 callback 취소
                    clickTriggerRunnable?.let {
                        if (deltaX > touchSlop || deltaY > touchSlop) {
                            handler.removeCallbacks(clickTriggerRunnable)
                            //clickTriggetRunnable = null
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    clickTriggerRunnable?.let {
                        val triggered = it.isClickTriggered
                        handler.removeCallbacks(it)
                        clickTriggerRunnable = null

                        return when {
                            triggered -> {
                                //Todo
                               Timber.d("Recyclerview Touched")
                                true
                            }
                            else -> super.onTouchEvent(this)
                        }
                    }
                }
                else -> super.onTouchEvent(e)
            }
        }
        return super.onTouchEvent(e)
    }

    inner class ClickTriggerRunnable : Runnable {

        var isClickTriggered = false
        override fun run() {
            isClickTriggered = true
        }
    }
}