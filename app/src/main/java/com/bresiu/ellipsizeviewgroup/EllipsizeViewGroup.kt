package com.bresiu.ellipsizeviewgroup

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.doOnPreDraw

class EllipsizeViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val childViews = mutableListOf<View>()
    private var rootWidth = 0
    var childWidth = 0
    var space = 0

    init {
        orientation = LinearLayout.HORIZONTAL
        doOnPreDraw {
            rootWidth = measuredWidth
            Log.d("BRS", "root width: $measuredWidth")
            addViews()
            Log.d("BRS", "Group size: $childCount")
        }
    }

    private fun addViews() {
        if (childViews.isNotEmpty()) {
            measureChildIfNeeded(childViews)
            measureSpaceIfNeeded()
            if (space < childViews.size) {
                for (i in 0 until space - 1) {
                    super.addView(childViews[i])
                }
                super.addView(addMoreView())
            } else {
                childViews.forEach {
                    super.addView(it)
                }
            }
            Log.d("BRS", "Adding ${childViews.size} child views")

            childViews.clear()
        }
    }

    private fun addMoreView(): View {
        return LayoutInflater.from(context).inflate(R.layout.more, this, false)
    }

    // todo: support addView after preDraw
    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        childViews.add(child)
    }

    private fun measureSpaceIfNeeded() {
        if (space == 0) {
            space = rootWidth / childWidth
            Log.d("BRS", "Space for: $space")
        }
    }

    private fun measureChildIfNeeded(views: List<View>) {
        if (childWidth == 0) {
            val view = views[0]
            view.measure(0, 0)
            childWidth = view.measuredWidth
            Log.d("BRS", "child width: ${view.measuredWidth}")
        }
    }
}
