package com.example.tracklist.ui.list.adapter

import com.example.tracklist.R

class HeaderWrapper(val dateAsOf: String) : TrackEntryWrapper
{
    override val viewType: Int
        get() = R.layout.i_header
}