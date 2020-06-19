package com.android.domain.entities

import com.android.domain.repositories.ILastFMRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetArtistUseCase: KoinComponent {
    private val artistRepository: ILastFMRepository by inject()
    fun searchArtist(query: String) = artistRepository.searchArtist(query)
    fun getTopArtist() = artistRepository.getTopArtist()
    fun getArtistAlbums(artistId: String)= artistRepository.getArtistAlbums(artistId)
}
