package com.android.demo.search.view.ui

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.demo.R
import com.android.demo.search.base.BaseFragment
import com.android.demo.search.utils.Data
import com.android.demo.search.utils.DebouncingQueryTextListener
import com.android.demo.search.utils.EMPTY
import com.android.demo.search.utils.Status
import com.android.demo.search.utils.Status.LOADING
import com.android.demo.search.view.adapter.ArtistListAdapter
import com.android.demo.search.view.adapter.OnArtistClickListener
import com.android.demo.search.viewmodel.ArtistViewModel
import com.android.domain.entities.Artist
import kotlinx.android.synthetic.main.fragment_artist.etSearch
import kotlinx.android.synthetic.main.fragment_artist.progressBarLayout
import kotlinx.android.synthetic.main.fragment_artist.rvArtist
import kotlinx.android.synthetic.main.fragment_artist.textViewLable
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArtistFragment : BaseFragment(), OnArtistClickListener {

    private val viewModel by viewModel<ArtistViewModel>()
    private val artistAdapter = ArtistListAdapter()

    companion object {
        const val TAG = "ArtistFragment"
        fun newInstance(): ArtistFragment {
            return ArtistFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscibeObservers()
    }

    private fun subscibeObservers() {
        viewModel.getTopArtisListLiveData().observe(this, Observer { response ->
            handleTopArtistList(response)
        })
        viewModel.getArtistSearchList().observe(this, Observer { response ->
            handleSearchArtistResponse(response)
        })
    }

    private fun handleSearchArtistResponse(response: Data<List<Artist>>) {
        showLoading(response.responseType == LOADING)
        when (response.responseType) {
            Status.SUCCESSFUL -> {
                response.data?.let { artistList ->
                    textViewLable.setText(R.string.searchResults)
                    handleTopArtistListSuccess(artistList)
                }
            }
            Status.ERROR -> {
                response.error?.message?.let { message -> showErrorMessage(message) }
            }

        }
    }

    private fun initializeRecyclerView() {
        rvArtist.layoutManager = LinearLayoutManager(context)
        rvArtist.adapter = artistAdapter
        artistAdapter.setonArtistClickListener(this)
    }

    private fun handleTopArtistList(response: Data<List<Artist>>) {
        showLoading(response.responseType == LOADING)
        when (response.responseType) {
            Status.SUCCESSFUL -> {
                response.data?.let { artistList ->
                    textViewLable.setText(R.string.topArtists)
                    handleTopArtistListSuccess(artistList)
                }
            }
            Status.ERROR -> {
                response.error?.message?.let { message -> showErrorMessage(message) }
            }

        }
    }

    private fun handleTopArtistListSuccess(data: List<Artist>) {
        artistAdapter.submitList(data)
        rvArtist.scrollToPosition(0)
        hideKeyboard()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBarLayout.visibility = View.VISIBLE
        } else {
            progressBarLayout.visibility = View.GONE
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_artist
    }

    override fun init(view: View) {
        initialize(view)
        getTopArtistList()
    }

    private fun initialize(view: View) {
        textViewLable.setText(R.string.topArtists)
        initializeRecyclerView()
        initSearch()
    }

    private fun getTopArtistList() {
        if (isNetworkAvailable())
            viewModel.getTopArtistList()
    }

    override fun onArtistClicked(artist: Artist) {
        callback?.showArtistTopAlbums(artist.name, artist.imageUrl)
    }

    private val textListener = DebouncingQueryTextListener(this@ArtistFragment.lifecycle) { newQuery ->
        newQuery.let {
            if (newQuery.isNullOrEmpty()) {
                getTopArtistList()
            } else
                handleSubmitQuery(newQuery)
        }
    }

    private fun initSearch() {
        etSearch.removeTextChangedListener(textListener)
        etSearch.setText(EMPTY)
        etSearch.addTextChangedListener(textListener)
    }

    private fun handleSubmitQuery(query: String?) {
        query?.let {
            viewModel.onSearchArtist(query)
        }
    }
}