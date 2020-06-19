package com.android.demo.search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.android.demo.search.utils.Data
import com.android.demo.search.utils.Status
import com.android.demo.search.viewmodel.base.BaseViewModel
import com.android.domain.entities.Album
import com.android.domain.entities.Artist
import com.android.domain.entities.GetArtistUseCase
import com.android.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ArtistViewModel(val usecase: GetArtistUseCase) : BaseViewModel() {

    private var artistSearchListMutableLiveData: MutableLiveData<Data<List<Artist>>> = MutableLiveData()
    private var artistTopListMutableLiveData: MutableLiveData<Data<List<Artist>>> = MutableLiveData()
    private var albumListMutableLiveData: MutableLiveData<Data<List<Album>>> = MutableLiveData()

    fun getArtistSearchList(): MutableLiveData<Data<List<Artist>>>{
        return artistSearchListMutableLiveData
    }

    fun getTopArtisListLiveData(): MutableLiveData<Data<List<Artist>>>{
        return artistTopListMutableLiveData
    }

    fun getAlbumListLiveData(): MutableLiveData<Data<List<Album>>>{
        return albumListMutableLiveData
    }

    fun onSearchArtist(query: String) = launch {
        artistSearchListMutableLiveData.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) { usecase.searchArtist(query) }) {
            is Result.Failure -> {
                artistSearchListMutableLiveData.value = Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                artistSearchListMutableLiveData.value = Data(responseType = Status.SUCCESSFUL, data = result.data)
            }
        }
    }

     fun getTopArtistList() = launch {
         artistTopListMutableLiveData.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) {
            usecase.getTopArtist() }) {
            is Result.Failure -> {
                artistTopListMutableLiveData.value = Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                artistTopListMutableLiveData.value = Data(responseType = Status.SUCCESSFUL, data = result.data)
            }
        }
    }

    fun getArtistAlbumList(artistId: String) = launch {
        albumListMutableLiveData.value = Data(responseType = Status.LOADING)
        when (val result = withContext(Dispatchers.IO) {
            usecase.getArtistAlbums(artistId) }) {
            is Result.Failure -> {
                albumListMutableLiveData.value = Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success -> {
                albumListMutableLiveData.value = Data(responseType = Status.SUCCESSFUL, data = result.data)
            }
        }
    }

}


