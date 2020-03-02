package com.koktajlbar.incognitobook.modules

import com.koktajlbar.incognitobook.view.CocktailsFragment
import org.koin.dsl.module

val koinFragmentModule = module {
    factory { CocktailsFragment() }
}