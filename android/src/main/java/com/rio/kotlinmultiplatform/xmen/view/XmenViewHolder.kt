package com.rio.kotlinmultiplatform.xmen.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.auto.factory.AutoFactory
import com.rio.kotlinmultiplatform.R
import com.rio.kotlinmultiplatform.common.ViewHolder
import com.rio.kotlinmultiplatform.common.ViewHolderFactory
import com.rio.kotlinmultiplatform.xmen.data.Xmen

@AutoFactory(implementing = [ViewHolderFactory::class])
class XmenViewHolder(parent: ViewGroup) : ViewHolder<Xmen, Nothing>(
    LayoutInflater.from(parent.context).inflate(
        R.layout.view_movie, parent, false
    )
) {

    override var listener: Nothing? = null

    override fun onBindViewHolder(item: Xmen) {
        val poster = itemView.findViewById<ImageView>(R.id.movie_image)
        val title = itemView.findViewById<TextView>(R.id.movie_title)

        Glide.with(itemView.context)
            .load(item.poster)
            .into(poster)

        title.text = item.title
    }

}