package com.example.tracklist.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tracklist.R
import com.example.tracklist.services.search.TrackBean
import com.example.tracklist.utils.ext.formatToAmount
import com.example.tracklist.utils.adapter.BaseVH
import com.example.tracklist.utils.ext.DATE_FORMAT_DATE_NO_TIME
import com.example.tracklist.utils.ext.changeStringDateFormat
import kotlinx.android.synthetic.main.i_header.view.*
import kotlinx.android.synthetic.main.i_track.view.*

class TrackListAdapter(private val listener: OnTrackSelectedListener) :
        RecyclerView.Adapter<BaseVH<TrackEntryWrapper>>()
{
    private val list = ArrayList<TrackEntryWrapper>()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<TrackEntryWrapper>
    {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType)
        {
            R.layout.i_track  -> TrackVH(inflater.inflate(viewType, parent, false)) as BaseVH<TrackEntryWrapper>
            R.layout.i_header -> HeaderVH(inflater.inflate(viewType, parent, false)) as BaseVH<TrackEntryWrapper>
            else              -> throw RuntimeException("Unidentified view type. $viewType is not specified. Please add this historyViewType definition at $this")
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BaseVH<TrackEntryWrapper>, position: Int)
    {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int = list[position].viewType

    fun updateList(list: List<TrackEntryWrapper>)
    {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class TrackVH(itemView: View) : BaseVH<EntryWrapper>(itemView), View.OnClickListener
    {
        var bean: TrackBean? = null

        init
        {
            itemView.card_view.setOnClickListener(this)
        }

        override fun bind(t: EntryWrapper)
        {
            this.bean = t.bean
            bean ?: return

            with(itemView)
            {
                textview_track_name.text = bean!!.name
                textview_genre.text = bean!!.genre
                textview_price.text = bean!!.price.toString().formatToAmount(bean!!.currency)

                Glide.with(context)
                        .load(bean!!.artworkUrl)
                        .placeholder(AppCompatResources.getDrawable(itemView.context, R.drawable.logo_placeholder_art))
                        .into(imageview_track_artwork)
            }
        }

        override fun onClick(view: View?)
        {
            bean?.let {
                listener.onTrackSelected(it)
            }
        }
    }

    inner class HeaderVH(itemView: View) : BaseVH<HeaderWrapper>(itemView)
    {
        override fun bind(t: HeaderWrapper)
        {
            val formattedDate = t.dateAsOf.changeStringDateFormat(from = DATE_FORMAT_DATE_NO_TIME)
            itemView.textview_as_of.text = itemView.context.getString(R.string.format_as_of,
                    formattedDate)
        }
    }
}

interface OnTrackSelectedListener
{
    fun onTrackSelected(bean: TrackBean)
}