package com.example.tracklist.services.search.remote

import com.example.tracklist.services.search.TrackBean
import com.google.gson.annotations.SerializedName

class SearchRespModel
{
    @SerializedName("results")
    var listTracks: List<TrackBean>? = ArrayList()

    var dateAsOf: String = ""
}