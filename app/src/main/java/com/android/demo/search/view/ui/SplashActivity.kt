package com.android.demo.search.view.ui

import android.os.Bundle
import android.os.Handler
import com.android.demo.R
import com.android.demo.search.base.BaseActivity
import com.android.demo.search.utils.SPLASH_TIME

class SplashActivity : BaseActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        navigateToArtistScreen();
    }

    private fun navigateToArtistScreen() {
        handler.postDelayed(Runnable {
            if (!isFinishing) {
                ArtistActivity.startActivity(this)
                finish()
            }
        }, SPLASH_TIME)
    }
}