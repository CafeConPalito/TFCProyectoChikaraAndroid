package com.cafeconpalito.chikara.ui.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cafeconpalito.chikara.R
import kotlin.math.abs

class DotIndicatorDecoration(
    context: Context
) : RecyclerView.ItemDecoration() {

    private val activeColor = ContextCompat.getColor(context, R.color.secondary)
    private val inactiveColor = ContextCompat.getColor(context, R.color.secondary)
    private val activeRadius = 10f // Radio del punto activo
    private val inactiveRadius = 5f // Radio de los puntos inactivos
    private val padding = 16f // Espacio entre los puntos

    val xactiveColor = Color.BLACK

    private val activePaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = activeColor
    }

    private val inactivePaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = inactiveColor
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val itemCount = parent.adapter?.itemCount ?: 0
        if (itemCount == 0) return

        val totalWidth = parent.width
        val totalHeight = parent.height
        val indicatorWidth = itemCount * 2 * inactiveRadius + (itemCount - 1) * padding
        val startX = (totalWidth - indicatorWidth) / 2
        val posY = totalHeight - 2 * activeRadius

        val layoutManager = parent.layoutManager as LinearLayoutManager
        //val activePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        val activePosition = findActivePosition(layoutManager, parent)


        for (i in 0 until itemCount) {
            val x = startX + i * (2 * inactiveRadius + padding)
            val paint = if (i == activePosition) activePaint else inactivePaint
            val radius = if (i == activePosition) activeRadius else inactiveRadius
            c.drawCircle(x, posY, radius, paint)
        }
    }
    private fun findActivePosition(layoutManager: LinearLayoutManager, parent: RecyclerView): Int {
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

        if (firstVisiblePosition == RecyclerView.NO_POSITION || lastVisiblePosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        val centerX = parent.width / 2
        var closestPosition = firstVisiblePosition
        var minDistance = Int.MAX_VALUE

        for (i in firstVisiblePosition..lastVisiblePosition) {
            val child = layoutManager.findViewByPosition(i) ?: continue
            val childCenterX = (child.left + child.right) / 2
            val distance = abs(centerX - childCenterX)
            if (distance < minDistance) {
                minDistance = distance
                closestPosition = i
            }
        }

        return closestPosition
    }

}