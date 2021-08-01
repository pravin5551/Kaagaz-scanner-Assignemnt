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

/**
 * In this adapter class im binding the view to the image_layout
 *
 */

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
            .load(entinty[position].image_uri)
            .into(holder.image)
        holder.image_name.text=entinty[position].image_name
    }

    override fun getItemCount(): Int {
        return entinty.size
    }

    /**
     * This ImageViewHolder knows all the views of image_layout to inflate on screen
     */

    class ImageViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        var timestamp :TextView = view.findViewById(R.id.tvTimeStamp)
        var image:ImageView = view.findViewById(R.id.Picimage)
        var image_name:TextView = view.findViewById(R.id.image_name)
    }
}