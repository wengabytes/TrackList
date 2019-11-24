package com.example.tracklist

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tracklist.di.databaseModule
import com.example.tracklist.di.networkModule
import com.example.tracklist.services.search.remote.iTunesRemoteSource
import com.example.tracklist.services.search.iTunesRepository
import com.example.tracklist.services.search.local.TrackDatabase
import com.example.tracklist.ui.list.vmFactoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application()
{
    override fun onCreate()
    {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(listOf(networkModule,
                    databaseModule,
                    iTunesRemoteSource,
                    iTunesRepository,
                    vmFactoryModule))
        }
    }
}