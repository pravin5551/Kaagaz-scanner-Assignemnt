package com.example.kaagazscanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kaagazscanner.database.ImageEntity
import com.example.kaagazscanner.repository.RepositoryKagaz

/**
 * This is a VM layer in the `MVVM` architecture, where we are notifying the Activity/view about the
 * response changes via live data
 */

//Passing repository in constructor to communicate between Repo and ViewModel
class ViewModelKaagaz(val repository:RepositoryKagaz) :ViewModel() {


    //Adding images and image details to Table via repo using Livedata
    fun addImageDetails(imageEntity: ImageEntity){
        repository.addImageDetails(imageEntity)
    }

    //Getting images list from ImageEntity using Livedata
    fun getImageDetails():LiveData<List<ImageEntity>>{
        return repository.getImageDetails()
    }

}