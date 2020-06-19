package com.android.demo.search.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.demo.R
import com.android.domain.entities.Album
import com.android.domain.entities.Artist
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_album.view.imageAlbum
import kotlinx.android.synthetic.main.item_album.view.textAlbum
import kotlinx.android.synthetic.main.item_artist.view.imageArtist
import kotlinx.android.synthetic.main.item_artist.view.textArtistName

class AlbumListAdapter : ListAdapter<Album, AlbumListAdapter.ItemAlbumListViewHolder>(
    AlbumDiffUtilsCallback()
) {

    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAlbumListViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_album, parent, false)
        return ItemAlbumListViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: ItemAlbumListViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun submitList(list: List<Album?>?) {
        super.submitList(list?.let { artistList -> ArrayList(artistList) })

    }

    inner class ItemAlbumListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageAlbum = itemView.imageAlbum
        private val tvAlbumName = itemView.textAlbum

        fun setAlbumData(album: Album) {
            tvAlbumName.setText(album.name)
            Glide.with(mContext).load(album.imageUrl).into(imageAlbum)
        }

        fun bind(position: Int) {
            val album = getItem(position)
            setAlbumData(album)
        }
    }
}

