package com.bresiu.ellipsizeviewgroup

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.doOnPreDraw

class EllipsizeViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val childViews = mutableListOf<View>()
    private var rootWidth = 0
    private var childWidth = 0
    private var spaceAtStart = 0
    private var spaceLeft = 0
    private var afterPreDraw = false
    private var isMoreVisible = false

    init {
        orientation = LinearLayout.HORIZONTAL
        doOnPreDraw {
            Log.d("BRS", "doOnPreDraw")
            afterPreDraw = true
            setViews()
        }
    }

    fun setViews(views: List<View>) {
        childViews.addAll(views)
        setViews()
    }

    private fun setViews() {
        Log.d("BRS", "setViews")
        measureIfNeeded()
        if (canAddViews()) {
            Log.d("BRS", "setViews, childViews: ${childViews.size}, space for $spaceLeft")
            if (spaceLeft < childViews.size) {
                for (i in 0 until spaceLeft - 1) {
                    super.addView(childViews[i])
                }
                addMoreIcon()
            } else {
                childViews.forEach {
                    super.addView(it)
                }
            }
            measureSpaceLeft()
            Log.d("BRS", "Adding ${childViews.size} child views")
            childViews.clear()
        } else if (shouldExchangeLastItemForMore()) {
            Log.d("BRS", "shouldExchangeLastItemForMore")
            exchangeLastItemForMore()
            childViews.clear()
        }
    }

    private fun exchangeLastItemForMore() {
        removeViewAt(childCount - 1)
        addMoreIcon()
    }

    private fun addMoreIcon() {
        val view = inflateMoreView()
        super.addView(view)
        isMoreVisible = true
        Log.d("BRS", "addMoreIcon")
    }

    private fun inflateMoreView(): View {
        return LayoutInflater.from(context).inflate(R.layout.more, this, false)
    }

    private fun measureIfNeeded() {
        if (shouldMeasure()) {
            measureRoot()
            measureChild()
            measureSpace()
            Log.d("BRS", "measure if needed; root: $rootWidth, child: $childWidth, space: $spaceAtStart")
        }
    }

    private fun measureRoot() {
        rootWidth = measuredWidth
        Log.d("BRS", "rootWidth: $rootWidth")
    }

    private fun measureChild() {
        if (childViews.isNotEmpty()) {
            val view = childViews[0]
            view.measure(0, 0)
            childWidth = view.measuredWidth
            Log.d("BRS", "child width: ${view.measuredWidth}")
        }
    }

    private fun measureSpace() {
        spaceAtStart = rootWidth / childWidth
        spaceLeft = spaceAtStart
        Log.d("BRS", "space for: $spaceAtStart")
    }

    private fun measureSpaceLeft() {
        spaceLeft = spaceAtStart - childCount
        Log.d("BRS", "space left: $spaceLeft")
    }

    private fun shouldMeasure(): Boolean {
        return !allMeasured() && readyToMeasure()
    }

    private fun allMeasured(): Boolean {
        return rootWidth > 0 && childWidth > 0
    }

    private fun readyToMeasure(): Boolean {
        return childViews.isNotEmpty() && afterPreDraw
    }

    private fun canAddViews(): Boolean {
        return allMeasured() && childViews.isNotEmpty() && spaceLeft > 0
    }

    private fun shouldExchangeLastItemForMore(): Boolean {
        Log.d("BRS", "allMeasured: ${allMeasured()}, childViews.isNotEmpty: ${childViews.isNotEmpty()}, spaceLeft: $spaceLeft, isMoreVisible: $isMoreVisible")
        return allMeasured() && childViews.isNotEmpty() && spaceLeft == 0 && !isMoreVisible
    }
}
