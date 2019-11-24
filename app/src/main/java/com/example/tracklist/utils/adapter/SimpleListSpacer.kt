package com.example.tracklist.utils.adapter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tracklist.R

class SimpleListSpacer(val context: Context,
                       private val left: Int = 0,
                       private val top: Int = 0,
                       private val right: Int = 0,
                       private val bottom: Int = 0,

                       private val shouldDoubleTop: Boolean = false) : RecyclerView.ItemDecoration()
{

    override fun getItemOffsets(outRect: Rect,
                                view: View,
                                parent: RecyclerView,
                                state: RecyclerView.State)
    {
        super.getItemOffsets(outRect, view, parent, state)

        var position = parent.getChildViewHolder(view)?.adapterPosition

        if (position == RecyclerView.NO_POSITION)
        {
            val oldPosition = parent.getChildViewHolder(view)?.oldPosition
            if (oldPosition == RecyclerView.NO_POSITION) return
            position = oldPosition
        }

        val topNew = if (position == 0 && shouldDoubleTop)
            top * 2
        else
            top

        val bottomNew = if (position == parent.adapter?.itemCount?.minus(1))
            context.resources.getDimensionPixelOffset(R.dimen.element_spacing_twice) * 2
        else
            bottom

        outRect.set(left, topNew, right, bottomNew)
    }

}