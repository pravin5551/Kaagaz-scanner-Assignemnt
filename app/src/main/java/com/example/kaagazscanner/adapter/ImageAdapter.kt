package com.example.kaagazscanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kaagazscanner.R
import com.example.kaagazscanner.database.ImageEntity

class ImageAdapter(private val entinty: List<ImageEntity>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_layout, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.timestamp.text = entinty[position].timestamp
        Glide.with(holder.image)
            .load(entinty[position].imagename)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return entinty.size
    }

    class ImageViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        var timestamp :TextView = view.findViewById(R.id.tvTimeStamp)
        var image:ImageView = view.findViewById(R.id.Picimage)

    }
}