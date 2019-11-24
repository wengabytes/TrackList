package com.example.tracklist.services.search.remote

import android.content.Context
import com.example.tracklist.utils.network.callWebservice
import com.example.tracklist.services.BaseAPI
import org.koin.dsl.module

val iTunesRemoteSource = module {
    single { ITunesRemoteSource(get(), get()) }
}

class ITunesRemoteSource(private val context: Context,
                         private val api: BaseAPI)
{
    suspend fun search(mapRequest: HashMap<String, String>) =
            callWebservice(context) {
                api.searchITunes(mapRequest)
            }
}