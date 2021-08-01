package com.example.kaagazscanner.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Query


@Dao
interface ImageDao {

    //Query for Inserting all the data to kaggazscanner table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addImageData(imageEntity: ImageEntity)

    //Query for getting an images list from ImageEntity class
    @Query("select * from kaagazscanner")
    fun getImageData(): LiveData<List<ImageEntity>>



    //Query to delete table
    @Delete
    fun deleteManager(imageEntity: ImageEntity)

    //Query to update table
    @Update
    fun updateManager(imageEntity: ImageEntity)
}