package com.example.tracklist.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.tracklist.R
import com.example.tracklist.base.BaseFragment
import com.example.tracklist.services.search.TrackBean
import com.example.tracklist.utils.ext.formatToAmount
import kotlinx.android.synthetic.main.f_details.*

class TrackDetailsFragment : BaseFragment<TrackDetailsFragmentVM>()
{
    companion object
    {
        const val KEY_TRACK_BEAN = "keyTrackBean"
    }

    // START: Implement Required Methods

    override fun provideVM() = ViewModelProviders.of(this, factory)[TrackDetailsFragmentVM::class.java]

    // END  : Implement Required Methods

    // START: Callbacks

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.f_details, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<TrackBean>(KEY_TRACK_BEAN)?.let { bean ->
            Glide.with(view)
                    .load(bean.artworkUrl)
                    .placeholder(AppCompatResources.getDrawable(context!!, R.drawable.logo_placeholder_art))
                    .into(imageview_track_artwork)

            textview_track_name.text = bean.name
            textview_genre.text = bean.genre
            textview_price.text = bean.price.toString().formatToAmount(bean.currency)
            textview_long_desc.text = bean.longDescription
        }

        baseInterface?.setAppBarTitle(getString(R.string.title_track_details))
        baseInterface?.isBackButtonVisible(true)
    }

    // END  : Callbacks
}