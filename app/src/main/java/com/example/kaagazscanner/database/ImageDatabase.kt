package com.example.kaagazscanner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ImageEntity::class], version = 1)
abstract class ImageDatabase: RoomDatabase() {

    abstract fun getImageDao() : ImageDao

    companion object{
        var INSTANCE : ImageDatabase? = null

        fun getImageDatabase(context: Context): ImageDatabase{
            if (INSTANCE == null){
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    ImageDatabase::class.java,
                    "KaagazScannerr.db"
                )

                builder.fallbackToDestructiveMigration()
                INSTANCE=builder.build()
                return INSTANCE!!
            }else{
                return INSTANCE!!
            }
        }
    }
}