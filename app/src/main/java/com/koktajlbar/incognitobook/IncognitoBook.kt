package com.koktajlbar.incognitobook

import android.app.Application
import com.koktajlbar.incognitobook.modules.koinDatabaseModule
import com.koktajlbar.incognitobook.modules.koinFragmentModule
import com.koktajlbar.incognitobook.modules.koinNetworkModule
import com.koktajlbar.incognitobook.modules.koinViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class IncognitoBook : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@IncognitoBook)
            modules(listOf(koinDatabaseModule, koinNetworkModule, koinViewModelModule, koinFragmentModule))
        }
    }
}