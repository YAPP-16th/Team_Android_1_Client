package com.eroom.erooja.feature.editgoal

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class EditGoalItemTouchHelperCallback(): ItemTouchHelper.Callback() {
    private lateinit var onItemMoveListener: OnItemMoveListener

    constructor(listener: OnItemMoveListener) : this() {
        onItemMoveListener = listener
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        onItemMoveListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    interface OnItemMoveListener {
        fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    }
}