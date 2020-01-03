package com.rio.kotlinmultiplatform.avenger.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.auto.factory.AutoFactory
import com.rio.kotlinmultiplatform.R
import com.rio.kotlinmultiplatform.common.Listener
import com.rio.kotlinmultiplatform.common.ViewHolderFactory
import com.rio.kotlinmultiplatform.avenger.data.Avenger
import com.rio.kotlinmultiplatform.common.AdapterClickListener
import com.rio.kotlinmultiplatform.common.ViewHolder

@AutoFactory(implementing = [ViewHolderFactory::class])
class AvengerViewHolder(parent: ViewGroup) : ViewHolder<Avenger, AdapterClickListener<Avenger>>(LayoutInflater.from(parent.context).inflate(
    R.layout.view_movie, parent, false)) {

    override var listener: AdapterClickListener<Avenger>? = null

    override fun onBindViewHolder(item: Avenger) {
        val poster = itemView.findViewById<ImageView>(R.id.movie_image)
        val title = itemView.findViewById<TextView>(R.id.movie_title)

        Glide.with(itemView.context)
            .load(item.poster)
            .into(poster)

        title.text = item.title

        itemView.setOnClickListener {
            listener?.onItemClicked(item)
        }
    }

}
