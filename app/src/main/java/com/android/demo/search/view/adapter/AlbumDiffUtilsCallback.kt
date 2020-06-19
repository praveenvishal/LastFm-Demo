package com.android.demo.search.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.android.domain.entities.Album
import com.android.domain.entities.Artist

class AlbumDiffUtilsCallback : DiffUtil.ItemCallback<Album>() {

    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return (oldItem.mbid == newItem.mbid)
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return (oldItem.mbid == newItem.mbid)
    }
}