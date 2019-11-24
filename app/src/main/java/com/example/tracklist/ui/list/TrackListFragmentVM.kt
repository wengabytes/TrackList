package com.example.tracklist.ui.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.tracklist.R
import com.example.tracklist.base.BaseVM
import com.example.tracklist.utils.network.NetworkResult
import com.example.tracklist.services.search.ITunesRepository
import com.example.tracklist.services.search.TrackBean
import com.example.tracklist.ui.list.adapter.EntryWrapper
import com.example.tracklist.ui.list.adapter.HeaderWrapper
import com.example.tracklist.ui.list.adapter.TrackEntryWrapper
import com.example.tracklist.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackListFragmentVM(context: Context,
                          private val repo: ITunesRepository) : BaseVM(context)
{

    val ldListTracks = MutableLiveData<List<TrackEntryWrapper>?>()

    init
    {
        requestList(false)
    }

    fun requestList(refreshAll: Boolean)
    {

        if (job?.isActive != true)
            job?.cancel()

        job = scope.launch {
            withContext(Dispatchers.Main) {
                ldLoading.value = true
            }

            val result = withContext(Dispatchers.IO) {
                repo.search(refreshAll = refreshAll)
            }

            withContext(Dispatchers.Main) {
                when (result)
                {
                    is NetworkResult.Success ->
                    {
                        if (result.data.listTracks.isNullOrEmpty())
                        {
                            ldError.value = Event(context.getString(R.string.errmsg_list_empty))
                        }
                        else
                        {
                            val list = ArrayList<TrackEntryWrapper>().apply {
                                add(HeaderWrapper(result.data.dateAsOf))
                                val tempList = result.data.listTracks?.map {
                                    EntryWrapper(it)
                                } as Iterable<EntryWrapper>

                                addAll(tempList)
                            }
                            ldListTracks.value = list
                        }
                    }
                    is NetworkResult.Error   ->
                    {
                        ldError.value = Event(result.errorMessage)
                    }
                }

                ldLoading.value = false
            }
        }
    }
}
