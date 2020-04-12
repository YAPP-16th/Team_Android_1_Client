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
        val ANIMATION_DURATION: Long = 200
    }
    var isShowing: Boolean = false
    var isHiding: Boolean = false
    //var dyDirectionSum: Int = 0


    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: Button, directTargetChild: View, target: View, axes: Int): Boolean {
        return true
    }
    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: Button, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)

        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            hideView(child)
        } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
            showView(child)
        }
    }

    private fun hideView(view: View) {
        if (isHiding || view.visibility != View.VISIBLE)
            return
        var animator: ViewPropertyAnimator = view.animate()
            .translationY(view.height.toFloat())
            .setInterpolator(INTERPOLATOR)
            .setDuration(ANIMATION_DURATION)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                isHiding = false
                view.visibility = View.INVISIBLE
            }
            override fun onAnimationCancel(animation: Animator?) {
                isHiding = false
                showView(view)
            }
            override fun onAnimationStart(animation: Animator?) {
                isHiding = true
            }
        })
        animator.start()
    }

    private fun showView(view: View) {
        if (isShowing || view.visibility == View.VISIBLE)
            return
        var animator: ViewPropertyAnimator = view.animate()
            .translationY(0f)
            .setInterpolator(INTERPOLATOR)
            .setDuration(ANIMATION_DURATION)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                isShowing = false
            }
            override fun onAnimationCancel(animation: Animator?) {
                isShowing = false
                hideView(view)
            }
            override fun onAnimationStart(animation: Animator?) {
                isShowing = true
                view.visibility = View.VISIBLE
            }
        })
        animator.start()
    }
}