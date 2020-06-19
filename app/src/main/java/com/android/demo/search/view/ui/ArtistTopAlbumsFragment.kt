package com.android.demo.search.view.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.demo.R
import com.android.demo.search.base.BaseFragment
import com.android.demo.search.utils.Data
import com.android.demo.search.utils.DebouncingQueryTextListener
import com.android.demo.search.utils.Status
import com.android.demo.search.utils.Status.LOADING
import com.android.demo.search.view.adapter.AlbumListAdapter
import com.android.demo.search.view.adapter.ArtistListAdapter
import com.android.demo.search.view.adapter.OnArtistClickListener
import com.android.demo.search.viewmodel.ArtistViewModel
import com.android.domain.entities.Album
import com.android.domain.entities.Artist
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_artist.etSearch
import kotlinx.android.synthetic.main.fragment_artist.progressBarLayout
import kotlinx.android.synthetic.main.fragment_artist.rvArtist
import kotlinx.android.synthetic.main.fragment_artist_top_albums.detailLoaderView
import kotlinx.android.synthetic.main.fragment_artist_top_albums.imageOverView
import kotlinx.android.synthetic.main.fragment_artist_top_albums.progressBar
import kotlinx.android.synthetic.main.fragment_artist_top_albums.recyclerViewAlbums
import kotlinx.android.synthetic.main.fragment_artist_top_albums.textTitle
import kotlinx.android.synthetic.main.item_album.textAlbum
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArtistTopAlbumsFragment : BaseFragment() {

    private val viewModel by viewModel<ArtistViewModel>()
    private val albumListAdapter = AlbumListAdapter()

    companion object {
        const val TAG = "ArtistTopAlbumsFragment"
        const val KEY_EXTRA_ARTIST_ID = "KEY_EXTRA_ARTIST_ID"
        const val KEY_EXTRA_ARTIST_IMAGE = "KEY_EXTRA_ARTIST_IMAGE"

        fun newInstance(artistName: String?, artistImage: String?): ArtistTopAlbumsFragment {
            val bundle = Bundle()
            bundle.putString(KEY_EXTRA_ARTIST_ID, artistName)
            bundle.putString(KEY_EXTRA_ARTIST_IMAGE, artistImage)
            val artistTopAlbumsFragment = ArtistTopAlbumsFragment()
            artistTopAlbumsFragment.arguments = bundle
            return artistTopAlbumsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscibeObservers()
    }

    private fun subscibeObservers() {
        viewModel.getAlbumListLiveData().observe(this, Observer { response ->
            handleArtistAlbumList(response)
        })
    }

    private fun initializeRecyclerView() {
        recyclerViewAlbums.layoutManager = LinearLayoutManager(context)
        recyclerViewAlbums.adapter = albumListAdapter
    }

    private fun handleArtistAlbumList(response: Data<List<Album>>) {
        showLoading(response.responseType == LOADING)
        when (response.responseType) {
            Status.SUCCESSFUL -> {
                response.data?.let { albumList ->
                    handleArtistAlbumListSuccess(albumList)
                }
            }
            Status.ERROR -> {
                response.error?.message?.let { message -> showErrorMessage(message) }
            }

        }
    }

    private fun handleArtistAlbumListSuccess(data: List<Album>) {
        albumListAdapter.submitList(data)
        recyclerViewAlbums.scrollToPosition(0)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            detailLoaderView.visibility = View.VISIBLE
            recyclerViewAlbums.visibility = View.INVISIBLE
        } else {
            detailLoaderView.visibility = View.GONE
            recyclerViewAlbums.visibility = View.VISIBLE
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_artist_top_albums
    }

    override fun init(view: View) {
        initialize(view)
        arguments?.getString(KEY_EXTRA_ARTIST_ID)?.let {
                artistId ->
            getArtistTopAlbums(artistId)
            textTitle.setText(String.format(getString(R.string.textArtistTitle), artistId))
        }
    }

    private fun getArtistTopAlbums(artistId: String) {
        if (isNetworkAvailable())
            viewModel.getArtistAlbumList(artistId)
    }

    private fun initialize(view: View) {
        initializeRecyclerView()
        arguments?.getString(KEY_EXTRA_ARTIST_IMAGE)?.let { artistImage ->
            Glide.with(requireActivity()).load(artistImage).into(imageOverView)
        }
    }
}