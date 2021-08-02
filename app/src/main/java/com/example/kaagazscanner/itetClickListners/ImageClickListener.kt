package com.example.kaagazscanner.itetClickListners

import com.example.kaagazscanner.database.ImageEntity


interface ImageClickListener {
    fun onImageClicked(imageEntity: ImageEntity)
}