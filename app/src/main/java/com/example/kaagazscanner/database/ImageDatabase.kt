package com.example.kaagazscanner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This is a Room database class , here we are using singleton to create only one object of
 * database to insert, update, delete and ll database operation
 */
@Database(entities = [ImageEntity::class], version = 1)
abstract class ImageDatabase: RoomDatabase() {

    abstract fun getImageDao() : ImageDao

    companion object{
        var INSTANCE : ImageDatabase? = null

        fun getImageDatabase(context: Context): ImageDatabase{

            //checking if the insance has already created or not
            if (INSTANCE == null){
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    ImageDatabase::class.java,

                    //database name here
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