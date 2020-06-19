package com.android.data.service

import com.android.data.service.api.LastFmApi
import com.android.domain.entities.Album
import com.android.domain.entities.Artist
import com.android.domain.utils.Result

class ArtistApiService {

    private val api: LastFMRequestGenerator = LastFMRequestGenerator()

    fun searchArtistQuery(query: String): Result<List<Artist>> {
        val callResponse = api.createService(LastFmApi::class.java).searchArtist(query)
        val response = callResponse.execute()
        response?.let {
            if (response.isSuccessful) {
                response.body()?.results?.artistmatches?.artist?.let { artistListResponse ->
                    return Result.Success(artistListResponse)
                }
            }
        } ?: kotlin.run {
            return Result.Failure(java.lang.Exception(""))
        }
        return Result.Failure(java.lang.Exception(""))
    }

    fun getTopArtists(): Result<List<Artist>> {
        val callResponse = api.createService(LastFmApi::class.java).getTopArtists()
        val response = callResponse.execute()
        response?.let {
            if (response.isSuccessful) {
                response.body()?.artists?.artist?.let { artistListResponse ->
                    return Result.Success(artistListResponse)
                }
            }
        } ?: kotlin.run {
            return Result.Failure(java.lang.Exception(""))
        }
        return Result.Failure(java.lang.Exception(""))
    }

    fun getArtistAlbums(artistId:String): Result<List<Album>> {
        val callResponse = api.createService(LastFmApi::class.java).artistAlbums(artistId)
        val response = callResponse.execute()
        response?.let {
            if (response.isSuccessful) {
                response.body()?.topalbums?.album?.let { albumListResponse ->
                    return Result.Success(albumListResponse)
                }
            }
        } ?: kotlin.run {
            return Result.Failure(java.lang.Exception(""))
        }
        return Result.Failure(java.lang.Exception(""))
    }
}
