package com.android.data.repositories

import com.android.data.service.ArtistApiService
import com.android.domain.entities.Album
import com.android.domain.entities.Artist
import com.android.domain.repositories.ILastFMRepository
import com.android.domain.utils.Result

class ArtistRepositoryImpl(
    private val artistApiService: ArtistApiService) : ILastFMRepository  {

    override fun searchArtist(query: String): Result<List<Artist>> = artistApiService.searchArtistQuery(query)

    override fun getTopArtist(): Result<List<Artist>> =artistApiService.getTopArtists()

    override fun getArtistAlbums(artistId: String): Result<List<Album>> =artistApiService.getArtistAlbums(artistId)
}
