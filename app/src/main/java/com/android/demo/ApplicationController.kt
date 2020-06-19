package com.android.demo

import android.app.Application
import com.android.demo.search.di.useCasesModule
import com.android.demo.search.di.viewModelsModule
import com.android.di.repositoriesModule
import org.koin.core.context.startKoin

class ApplicationController: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(repositoriesModule, viewModelsModule, useCasesModule))
        }
    }
}
