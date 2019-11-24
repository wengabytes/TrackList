package com.example.tracklist.utils.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.tracklist.ui.list.adapter.TrackEntryWrapper

abstract class BaseVH<T: TrackEntryWrapper>(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    abstract fun bind(t: T)
}