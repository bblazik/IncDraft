package com.koktajlbar.incognitobook.modules

import androidx.lifecycle.ViewModel
import com.koktajlbar.incognitobook.view.CocktailsFragment
import com.koktajlbar.incognitobook.viewModel.CocktailsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CocktailsListModule {
    @ContributesAndroidInjector(modules = [
        ViewModelBuilder::class
    ])

    internal abstract fun cocktailsFragment(): CocktailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(CocktailsViewModel::class)
    abstract fun bindViewModel(viewmodel: CocktailsViewModel): ViewModel
}