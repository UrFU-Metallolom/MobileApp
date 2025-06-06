package com.github.viscube.greenhouse.di

import android.content.Context
import androidx.room.Room
import com.github.viscube.greenhouse.database.Database
import com.github.viscube.greenhouse.database.PrepopulatePresetsUseCase
import org.koin.dsl.module

val dbModule = module {
    single { DatabaseBuilder.getInstance(get()) }
    single { PrepopulatePresetsUseCase(get()) }
}

object DatabaseBuilder {
    private var INSTANCE: Database? = null

    fun getInstance(context: Context): Database {
        if (INSTANCE == null) {
            synchronized(Database::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "devices"
        ).fallbackToDestructiveMigration(false)
            .build()
}