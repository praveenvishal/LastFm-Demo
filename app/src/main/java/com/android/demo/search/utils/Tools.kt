package com.android.demo.search.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.android.demo.search.view.ui.ArtistFragment
import com.android.demo.search.view.ui.ArtistFragment.Companion

object Tools {

    fun hideKeyBoard(activity: FragmentActivity) {
        val view = activity.currentFocus
        view?.let { v ->
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun showToastMessage(msg: String,context: FragmentActivity) {
        msg?.let {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun isNetworkAvailable(activity: FragmentActivity?,TAG:String): Boolean {
        var result = false
        try {
            activity?.let { activity ->
                result =
                    (activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return result
    }
}
