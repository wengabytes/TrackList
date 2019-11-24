package com.example.tracklist.services.search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_date_as_of")
class DateAsOfBean
{
    @PrimaryKey
    var dateAsOf: String = ""
}