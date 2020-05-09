package com.eroom.domain.customview

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Interpolator
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class AddListScrollBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<Button>(context, attrs) {
    companion object {
        val INTERPOLATOR: Interpolator = FastOutSlowInInterpolator()
        const val ANIMATION_DOWN_DURATION: Long = 500
        const val ANIMATION_UP_DURATION: Long = 650
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: Button, directTargetChild: View, target: View, axes: Int): Boolean {
        return true
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: Button,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )

        if (dyConsumed > 0) {
            hideView(child)
        } else {
            showView(child)
        }
    }

    private fun hideView(view: View) {
        if (view.visibility == View.INVISIBLE)
            return
        var animator: ViewPropertyAnimator = view.animate()
            .translationY(view.height.toFloat())
            .setInterpolator(INTERPOLATOR)
            .setDuration(ANIMATION_DOWN_DURATION)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.INVISIBLE
                showView(view)
            }
            override fun onAnimationCancel(animation: Animator?) {
                view.visibility = View.INVISIBLE
                showView(view)
            }
            override fun onAnimationStart(animation: Animator?) {}
        })
        animator.start()
    }

    private fun showView(view: View) {
        if (view.visibility == View.VISIBLE)
            return
        view.translationY = view.height.toFloat()
        var animator: ViewPropertyAnimator = view.animate()
            .translationY(0f)
            .setInterpolator(INTERPOLATOR)
            .setDuration(ANIMATION_UP_DURATION)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
                view.visibility = View.VISIBLE
            }
        })
        animator.start()
    }
}