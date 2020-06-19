package com.android.demo.search.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.demo.R
import com.android.domain.entities.Artist
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_artist.view.imageArtist
import kotlinx.android.synthetic.main.item_artist.view.textArtistName

class ArtistListAdapter : ListAdapter<Artist, ArtistListAdapter.ItemArtistListViewHolder>(
    ArtistDiffUtilsCallback()
) {

    private var onArtistClickListener: OnArtistClickListener? = null
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArtistListViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artist, parent, false)
        return ItemArtistListViewHolder(itemView)
    }

    fun setonArtistClickListener(listener: OnArtistClickListener) {
        onArtistClickListener = listener
    }

    override fun onBindViewHolder(holder: ItemArtistListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun submitList(list: List<Artist?>?) {
        super.submitList(list?.let { artistList -> ArrayList(artistList) })

    }

    inner class ItemArtistListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewArtist = itemView.imageArtist
        private val tvArtistName = itemView.textArtistName

        fun setArtistData(artist: Artist) {
            tvArtistName.setText(artist.name)
            Glide.with(mContext).load(artist.imageUrl).into(imageViewArtist)
        }

        fun bind(position: Int) {
            val artist = getItem(position)
            setArtistData(artist)
            itemView.setOnClickListener {
                onArtistClickListener?.onArtistClicked(artist)
            }
        }
    }
}

interface OnArtistClickListener {
    fun onArtistClicked(artist: Artist)
}
