package com.example.tracklist.services.search

import com.example.tracklist.services.search.local.TrackDao
import com.example.tracklist.services.search.remote.ITunesRemoteSource
import com.example.tracklist.services.search.remote.SearchRespModel
import com.example.tracklist.utils.ext.DATE_FORMAT_DATE_NO_TIME
import com.example.tracklist.utils.ext.getCurrentDate
import com.example.tracklist.utils.ext.getFormattedStringDate
import com.example.tracklist.utils.network.NetworkResult
import kotlinx.coroutines.delay
import org.koin.dsl.module
import java.net.URLEncoder

val iTunesRepository = module {
    single { ITunesRepository(get(), get()) }
}

class ITunesRepository(private val dao: TrackDao,
                       private val remoteSource: ITunesRemoteSource)
{
    suspend fun search(term: String = "star",
                       country: String = "au",
                       media: String = "movie",
                       refreshAll: Boolean): NetworkResult<SearchRespModel>
    {
        val listLocal = dao.getAll()

        return if (listLocal.isNullOrEmpty() || refreshAll)
        {
            val result = requestFromRemote(term, country, media)

            if (result is NetworkResult.Success)
            {
                saveTracks(result.data.listTracks)
                result.data.dateAsOf = getCurrentDate().getFormattedStringDate(dateFormat = DATE_FORMAT_DATE_NO_TIME)
            }

            return result
        }
        else
        {
            NetworkResult.Success(SearchRespModel().apply {
                listTracks = listLocal
                dateAsOf = dao.getAsOfDate()
            })
        }
    }

    private suspend fun requestFromRemote(term: String = "star",
                                          country: String = "au",
                                          media: String = "movie"): NetworkResult<SearchRespModel>
    {
        return remoteSource.search(HashMap<String, String>().apply {
            put("term", URLEncoder.encode(term, "UTF-8"))
            put("country", URLEncoder.encode(country, "UTF-8"))
            put("media", URLEncoder.encode(media, "UTF-8"))
        })
    }

    private suspend fun saveTracks(list: List<TrackBean>?)
    {
        if (list.isNullOrEmpty())
            return

        dao.deleteAllTracks()
        dao.deleteDateAsOf()

        val dateAsOfBean = DateAsOfBean().apply {
            dateAsOf = getCurrentDate().getFormattedStringDate(dateFormat = DATE_FORMAT_DATE_NO_TIME)
        }
        dao.insertDateAsOf(dateAsOfBean)
        dao.insertAll(*list.toTypedArray())
    }
}