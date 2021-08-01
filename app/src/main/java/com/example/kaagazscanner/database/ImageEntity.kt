package com.example.kaagazscanner.database

import androidx.room.*


@Entity(tableName = "kaagazscanner")
data class ImageEntity(
    @ColumnInfo(name = "imagename") var image_uri :String,
    @ColumnInfo(name = "timestamp") var timestamp : String,
    @ColumnInfo(name = "album") var album:String,
    @ColumnInfo(name = "image_name") var image_name:String,

//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image:Byte
) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name= "id") var id:Int?=null

}