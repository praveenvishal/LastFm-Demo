package com.android.demo.search.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class DebouncingQueryTextListener(lifecycle: Lifecycle,
    private val onDebouncingQueryTextChange: (String?) -> Unit) : TextWatcher, LifecycleObserver {

    var debouncePeriod: Long = QUERY_DEBOUNCE_TIME

    init {
        lifecycle.addObserver(this)
    }

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    private var searchJob: Job? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        searchJob?.cancel()
    }

    override fun afterTextChanged(query: Editable?) {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            query?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(query.toString())
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // handle before text - Not required
    }

    override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
        // handle before text - Not required

    }
}