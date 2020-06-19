package com.android.demo.search.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.demo.R

abstract class  BaseActivity : AppCompatActivity() {


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    protected fun setFragment(fragment: Fragment,isAddToBackStack:Boolean,fragmentTag:String){
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.container,fragment,fragmentTag)
        if (isAddToBackStack)transaction.addToBackStack(fragmentTag)
        transaction.commit()
    }

    protected fun addFragment(fragment: Fragment,isAddToBackStack:Boolean,fragmentTag:String){
        val transaction = supportFragmentManager.beginTransaction().add(R.id.container,fragment,fragmentTag)
        if (isAddToBackStack)transaction.addToBackStack(fragmentTag)
        transaction.commit()
    }


}