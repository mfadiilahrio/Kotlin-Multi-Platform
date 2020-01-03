package com.rio.kotlinmultiplatform.avenger.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rio.kotlinmultiplatform.common.AdapterClickListener
import com.rio.kotlinmultiplatform.common.RecyclerViewAdapter
import com.rio.kotlinmultiplatform.common.ViewHolder
import com.rio.kotlinmultiplatform.common.ViewHolderFactory

class AvengerAdapter<E>(private val mViewHolderFactories: Map<Int, ViewHolderFactory>) :
    RecyclerViewAdapter<AdapterClickListener<E>, E, RecyclerView.ViewHolder>(),
    AdapterClickListener<E> {

    override var listener: AdapterClickListener<E>? = null

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < list.size) {
            val item = holder as ViewHolder<E, AdapterClickListener<E>>

            item.listener = this
            item.onBindViewHolder(list[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = mViewHolderFactories[viewType]?.createViewHolder(parent)

        viewHolder?.let {
            return it
        }

        throw IllegalArgumentException("View holder not found")
    }

    override fun onItemClicked(model: E) {
        listener?.onItemClicked(model)
    }

}