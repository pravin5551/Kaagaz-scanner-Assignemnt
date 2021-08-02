package com.example.kaagazscanner.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "album_table")
data class AlbumEntity(

    @ColumnInfo(name = "albumName")
    var albumName: String,

    @ColumnInfo(name = "albumImg")
    var albumImg: String

) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var albumId: Long? = null
}