package com.example.kaagazscanner.itetClickListners

import com.example.kaagazscanner.database.AlbumEntity

interface AlbumClickListener {
    fun onAlbumClicked(albumEntity: AlbumEntity)
}