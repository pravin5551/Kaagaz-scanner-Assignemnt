package com.example.kaagazscanner.activities

import android.app.Application
import com.example.kaagazscanner.database.ImageDatabase
import com.example.kaagazscanner.repository.RepositoryKagaz


/**
 * This is the base class of all the activities so it get executed fist
 */
class KaagazApplication : Application(){

    val imageDao by lazy {

        val roomDataListener = ImageDatabase.getImageDatabase(this)
        roomDataListener.getImageDao()
    }

    val repository by lazy {
        RepositoryKagaz(imageDao)
    }
}