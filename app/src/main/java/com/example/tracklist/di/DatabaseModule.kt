package com.example.tracklist.di

import android.content.Context
import androidx.room.Room
import com.example.tracklist.services.search.local.TrackDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }
    single { provideDao(get()) }
}

fun provideDatabase(context: Context) =
        Room.databaseBuilder(context,
                TrackDatabase::class.java, "TracksLocalDatabase")
                .build()

fun provideDao(database: TrackDatabase) =
        database.trackDao()