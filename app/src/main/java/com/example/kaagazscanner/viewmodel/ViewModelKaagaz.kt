package com.example.kaagazscanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kaagazscanner.database.ImageEntity
import com.example.kaagazscanner.repository.RepositoryKagaz

class ViewModelKaagaz(val repository:RepositoryKagaz) :ViewModel() {

    fun addImageDetails(imageEntity: ImageEntity){
        repository.addImageDetails(imageEntity)
    }

    fun getImageDetails():LiveData<List<ImageEntity>>{
        return repository.getImageDetails()
    }

}