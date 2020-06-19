package com.android.demo.search.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.demo.search.utils.IMPLEMENT_DATASTATECHANGELISTENER
import com.android.demo.search.utils.OnFragmentInteractionListener
import com.android.demo.search.utils.Tools
import com.android.demo.search.view.ui.ArtistFragment
import com.android.demo.search.view.ui.ArtistFragment.Companion.TAG

abstract class BaseFragment : Fragment(),IBaseFragment {

    lateinit var callback: OnFragmentInteractionListener
    companion object {
        const val TAG = "BaseFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getLayout()?.let { layout ->
            return inflater.inflate(layout, container, false)
        } ?: kotlin.run {
            return null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.let {
            init(view)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callback = context as OnFragmentInteractionListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context $IMPLEMENT_DATASTATECHANGELISTENER")
        }
    }

    fun hideKeyboard() {
        activity?.let { activity ->
            Tools.hideKeyBoard(activity)
        }
    }

    fun showErrorMessage(msg: String) {
        activity?.let { context ->
           Tools.showToastMessage(msg,context)
        }
    }
    open fun isNetworkAvailable(): Boolean {
        return Tools.isNetworkAvailable(activity, TAG)
    }
}