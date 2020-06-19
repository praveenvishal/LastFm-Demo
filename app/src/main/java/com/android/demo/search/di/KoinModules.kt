package com.android.demo.search.di

import com.android.demo.search.viewmodel.ArtistViewModel
import com.android.domain.entities.GetArtistUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ArtistViewModel(get()) }
}

val useCasesModule = module {
    single { GetArtistUseCase() }
}


