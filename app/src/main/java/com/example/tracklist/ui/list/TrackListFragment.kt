package com.example.tracklist.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tracklist.R
import com.example.tracklist.base.BaseFragment
import com.example.tracklist.services.search.TrackBean
import com.example.tracklist.ui.details.TrackDetailsFragment
import com.example.tracklist.ui.list.adapter.OnTrackSelectedListener
import com.example.tracklist.ui.list.adapter.TrackEntryWrapper
import com.example.tracklist.ui.list.adapter.TrackListAdapter
import com.example.tracklist.utils.adapter.SimpleListSpacer
import kotlinx.android.synthetic.main.f_list.*

class TrackListFragment : BaseFragment<TrackListFragmentVM>(),
        OnTrackSelectedListener
{
    private lateinit var adapterTrackList: TrackListAdapter

    // START: Implement Required Methods

    override fun provideVM() = ViewModelProviders.of(this, factory)[TrackListFragmentVM::class.java]

    override fun onTrackSelected(bean: TrackBean)
    {
        findNavController().navigate(R.id.action_trackListFragment_to_trackDetailsFragment,
                bundleOf(TrackDetailsFragment.KEY_TRACK_BEAN to bean))
    }
    // END  : Implement Required Methods

    // START: Callbacks
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.f_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        baseInterface?.setAppBarTitle(getString(R.string.title_track_list))
        baseInterface?.isBackButtonVisible(false)

        with(recycler_view)
        {
            adapterTrackList = TrackListAdapter(
                    this@TrackListFragment
            ).also {
                adapter = it
            }
            layoutManager = LinearLayoutManager(context)

            val space = context.resources.getDimensionPixelSize(R.dimen.element_spacing_twice)
            addItemDecoration(
                    SimpleListSpacer(
                            context!!,
                            space,
                            space,
                            space,
                            0
                    )
            )
        }

        listenViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        listenVM()
    }
    // END  : Callbacks

    // START: Custom Methods
    private fun listenVM()
    {
        vm.ldLoading.observe(viewLifecycleOwner,
                Observer {isLoading ->
                    if (isLoading)
                        textview_message.isVisible = false

                    swipe_refresh_layout.isRefreshing = isLoading
                })

        vm.ldError.observe(viewLifecycleOwner,
                Observer {
                    it?.consume()?.let { message -> setupErrorMessage(message) }
                })

        vm.ldListTracks.observe(viewLifecycleOwner,
                Observer { setupRecyclerView(it) })
    }

    private fun listenViews()
    {
        swipe_refresh_layout.setOnRefreshListener {
            vm.requestList(true)
        }
    }

    private fun setupErrorMessage(message: String)
    {
        if (adapterTrackList.itemCount > 0)
        {
            baseInterface?.onError(message)
            textview_message.isVisible = false
        }
        else
        {
            with(textview_message)
            {
                isVisible = true
                text = getString(R.string.format_errmsg_swipe, message, getString(R.string.swipe_downwards_to_retry))
            }
        }
    }

    private fun setupRecyclerView(list: List<TrackEntryWrapper>?)
    {
        if (list.isNullOrEmpty())
            recycler_view.isVisible = false
        else
        {
            recycler_view.isVisible = true
            adapterTrackList.updateList(list)
        }
    }
    // END  : Custom Methods
}
