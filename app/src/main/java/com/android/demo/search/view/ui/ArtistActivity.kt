package com.android.demo.search.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.demo.R
import com.android.demo.search.base.BaseActivity
import com.android.demo.search.utils.OnFragmentInteractionListener

class ArtistActivity :BaseActivity(), OnFragmentInteractionListener {

    companion object{
        fun startActivity(context: Context){
            val intent= Intent(context,ArtistActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(
            ArtistFragment.newInstance(),false,
            ArtistFragment.TAG
        )
    }

    override fun showArtistTopAlbums(artistId: String?,artistImage: String?) {
        addFragment(
            ArtistTopAlbumsFragment.newInstance(artistId,artistImage),true,
            ArtistTopAlbumsFragment.TAG)
    }
}