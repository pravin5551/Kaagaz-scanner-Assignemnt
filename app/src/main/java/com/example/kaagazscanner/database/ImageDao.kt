package com.example.kaagazscanner.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Query


@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addImageData(imageEntity: ImageEntity)

    @Query("select * from kaagazscanner")
    fun getImageData(): LiveData<List<ImageEntity>>


//    @Query("update moneymanager set title= :newTitle, type= :newType, `album`=:newAmount where id =:id")
//    fun changeTask(id:Int,newTitle:String, newType:String,newAmount: String)


    @Delete
    fun deleteManager(imageEntity: ImageEntity)

    @Update
    fun updateManager(imageEntity: ImageEntity)
}