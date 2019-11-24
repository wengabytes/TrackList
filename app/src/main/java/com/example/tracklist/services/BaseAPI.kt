package com.example.tracklist.services

import com.example.tracklist.services.search.remote.SearchRespModel
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BaseAPI
{
    @GET("search?")
    suspend fun searchITunes(@QueryMap map: Map<String, String>): SearchRespModel
}