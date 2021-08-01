package com.example.kaagazscanner.repository

import androidx.lifecycle.LiveData
import com.example.kaagazscanner.database.ImageDao
import com.example.kaagazscanner.database.ImageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * This is a `M` layer in the `MVVM` architecture which gives us the data from the Database by Query from Dao
 */

//passing ImageDao in constructor to communicate from database
class RepositoryKagaz(val imageDao: ImageDao) {



    //Here all the data is to be added to entity Table via Dao
    fun addImageDetails(imageEntity: ImageEntity){
        CoroutineScope(Dispatchers.IO).launch {
            imageDao.addImageData(imageEntity)
        }
    }

    //Here I'm fetching data from Entity class table
    fun getImageDetails():LiveData<List<ImageEntity>>{
        return imageDao.getImageData()
    }
}