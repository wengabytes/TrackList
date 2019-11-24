package com.example.tracklist.services.search.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tracklist.services.search.DateAsOfBean
import com.example.tracklist.services.search.TrackBean

@Database(entities = [TrackBean::class, DateAsOfBean::class], version = 1)
abstract class TrackDatabase : RoomDatabase()
{
    abstract fun trackDao(): TrackDao
}