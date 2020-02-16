package com.koktajlbar.incognitobook.components

import android.content.Context
import com.koktajlbar.incognitobook.IncognitoBook
import com.koktajlbar.incognitobook.modules.ApplicationModule
import com.koktajlbar.incognitobook.modules.CocktailsListModule
import com.koktajlbar.incognitobook.repositories.CocktailRepository
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ApplicationModule::class,
            AndroidSupportInjectionModule::class,
            CocktailsListModule::class
        ]
)
interface ApplicationComponent : AndroidInjector<IncognitoBook> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}