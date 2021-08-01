package com.example.kaagazscanner.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kaagazscanner.repository.RepositoryKagaz
import com.example.kaagazscanner.viewmodel.ViewModelKaagaz

class ImageViewModelFactory(val repository: RepositoryKagaz):ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewModelKaagaz(repository) as T
    }

}