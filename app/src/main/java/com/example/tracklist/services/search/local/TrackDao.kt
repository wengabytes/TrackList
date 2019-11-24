package com.example.tracklist.services.search.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tracklist.services.search.DateAsOfBean
import com.example.tracklist.services.search.TrackBean

@Dao
interface TrackDao
{
    @Query("SELECT * FROM table_track")
    suspend fun getAll(): List<TrackBean>

    @Query("DELETE FROM table_track")
    suspend fun deleteAllTracks()

    @Insert
    suspend fun insertAll(vararg trackBean: TrackBean)

    @Query("DELETE FROM table_date_as_of")
    suspend fun deleteDateAsOf()

    @Insert
    suspend fun insertDateAsOf(dateAsOfBean: DateAsOfBean)

    @Query("SELECT * FROM table_date_as_of LIMIT 1")
    suspend fun getAsOfDate(): String
}