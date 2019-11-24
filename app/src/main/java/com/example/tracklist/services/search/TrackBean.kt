package com.example.tracklist.services.search

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_track")
class TrackBean() : Parcelable
{
    @PrimaryKey
    @SerializedName("trackId")
    var id: Int = 0

    @ColumnInfo(name = "trackName")
    @SerializedName("trackName")
    var name: String = ""

    @ColumnInfo(name = "artworkUrl100")
    @SerializedName("artworkUrl100")
    var artworkUrl: String = ""

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    var currency: String = ""

    @ColumnInfo(name = "trackPrice")
    @SerializedName("trackPrice")
    var price: Double = 0.0

    @ColumnInfo(name = "primaryGenreName")
    @SerializedName("primaryGenreName")
    var genre: String = ""

    @ColumnInfo(name = "longDescription")
    @SerializedName("longDescription")
    var longDescription: String = ""

    constructor(parcel: Parcel) : this()
    {
        id = parcel.readInt()
        name = parcel.readString() ?: ""
        artworkUrl = parcel.readString() ?: ""
        currency = parcel.readString() ?: ""
        price = parcel.readDouble()
        genre = parcel.readString() ?: ""
        longDescription = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(artworkUrl)
        parcel.writeString(currency)
        parcel.writeDouble(price)
        parcel.writeString(genre)
        parcel.writeString(longDescription)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<TrackBean>
    {
        override fun createFromParcel(parcel: Parcel): TrackBean
        {
            return TrackBean(parcel)
        }

        override fun newArray(size: Int): Array<TrackBean?>
        {
            return arrayOfNulls(size)
        }
    }

}