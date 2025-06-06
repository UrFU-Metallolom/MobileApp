package com.github.viscube.greenhouse

import android.app.Application
import com.github.viscube.greenhouse.database.PrepopulatePresetsUseCase
import com.github.viscube.greenhouse.di.dbModule
import com.github.viscube.greenhouse.di.rootModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(rootModule, dbModule)
        }

        val useCase: PrepopulatePresetsUseCase = getKoin().get()

        CoroutineScope(Dispatchers.IO).launch {
            useCase()
        }
    }
}