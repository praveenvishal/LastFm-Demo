package com.android.domain.repositories

import com.android.domain.entities.Album
import com.android.domain.entities.Artist
import com.android.domain.utils.Result

interface ILastFMRepository {
    fun searchArtist(query: String): Result<List<Artist>>
    fun getTopArtist(): Result<List<Artist>>
    fun getArtistAlbums(artistId: String):Result<List<Album>>
}