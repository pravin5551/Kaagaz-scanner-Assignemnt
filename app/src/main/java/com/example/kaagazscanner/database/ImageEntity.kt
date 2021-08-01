package com.example.kaagazscanner.database

import androidx.room.*

/**
 * Entity class of Room database to create table and thee details
 * Here  columns are created id, imagename, timestamp, album, image_name
 */
@Entity(tableName = "kaagazscanner")
data class ImageEntity(
    @ColumnInfo(name = "imagename") var image_uri :String,
    @ColumnInfo(name = "timestamp") var timestamp : String,
    @ColumnInfo(name = "album") var album:String,
    @ColumnInfo(name = "image_name") var image_name:String,

//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image:Byte
) {

    //here a unique primary key will generate for all the rows
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name= "id") var id:Int?=null

}