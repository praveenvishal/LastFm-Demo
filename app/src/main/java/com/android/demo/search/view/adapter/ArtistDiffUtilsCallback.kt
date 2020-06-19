package com.android.demo.search.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.android.domain.entities.Artist

class ArtistDiffUtilsCallback : DiffUtil.ItemCallback<Artist>() {

    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return (oldItem.mbid == newItem.mbid)
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return (oldItem.mbid == newItem.mbid)
    }
}