package com.android.demo.search.base

import android.view.View

interface IBaseFragment {
    fun getLayout(): Int?
    fun init(view: View)
}