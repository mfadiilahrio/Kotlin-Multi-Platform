package com.rio.kotlinmultiplatform.common

abstract class ViewHolder<in E, L>(itemView: android.view.View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    abstract var listener: L?

    abstract fun onBindViewHolder(item: E)
}