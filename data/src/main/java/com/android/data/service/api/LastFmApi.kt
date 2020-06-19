package com.android.data.service.api

import com.android.domain.response.ArtistApiResponse
import com.android.domain.response.ArtistSearchApiResponse
import com.android.domain.response.ArtistTopAlbumsApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {

    @GET("/2.0/?method=chart.gettopartists")
    fun getTopArtists(): Call<ArtistApiResponse>

    @GET("/2.0/?method=artist.search")
    fun searchArtist(@Query("artist") artist: String): Call<ArtistSearchApiResponse>

    @GET("/2.0/?method=artist.gettopalbums")
    fun artistAlbums(@Query("artist") artist: String): Call<ArtistTopAlbumsApiResponse>

}
