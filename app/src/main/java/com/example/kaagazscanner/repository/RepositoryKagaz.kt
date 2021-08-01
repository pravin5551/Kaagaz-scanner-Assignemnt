package com.example.kaagazscanner.repository

import androidx.lifecycle.LiveData
import com.example.kaagazscanner.database.ImageDao
import com.example.kaagazscanner.database.ImageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryKagaz(val imageDao: ImageDao) {

    fun addImageDetails(imageEntity: ImageEntity){
        CoroutineScope(Dispatchers.IO).launch {
            imageDao.addImageData(imageEntity)
        }
    }

    fun getImageDetails():LiveData<List<ImageEntity>>{
        return imageDao.getImageData()
    }
}