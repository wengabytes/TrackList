package com.example.tracklist.ui.list.adapter

import com.example.tracklist.R
import com.example.tracklist.services.search.TrackBean

class EntryWrapper(val bean: TrackBean) : TrackEntryWrapper
{
    override val viewType: Int
        get() = R.layout.i_track
}