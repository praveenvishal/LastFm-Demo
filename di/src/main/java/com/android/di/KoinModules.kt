package com.android.di

import com.android.data.repositories.ArtistRepositoryImpl
import com.android.data.service.ArtistApiService
import com.android.domain.repositories.ILastFMRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single { ArtistApiService() }
    single<ILastFMRepository> { ArtistRepositoryImpl(get()) }
}