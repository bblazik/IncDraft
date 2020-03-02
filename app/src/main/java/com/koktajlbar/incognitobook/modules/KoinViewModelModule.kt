package com.koktajlbar.incognitobook.modules

import com.koktajlbar.incognitobook.viewmodels.CocktailViewModel
import com.koktajlbar.incognitobook.viewmodels.CocktailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinViewModelModule = module {
    viewModel { CocktailViewModel(get()) }
    viewModel { CocktailsViewModel(get()) }
}