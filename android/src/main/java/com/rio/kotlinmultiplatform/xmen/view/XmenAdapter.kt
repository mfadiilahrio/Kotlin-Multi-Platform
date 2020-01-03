package com.rio.kotlinmultiplatform.xmen.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rio.kotlinmultiplatform.common.RecyclerViewAdapter
import com.rio.kotlinmultiplatform.common.ViewHolder
import com.rio.kotlinmultiplatform.common.ViewHolderFactory

class XmenAdapter<E>(private val mViewHolderFactories: Map<Int, ViewHolderFactory>) :
    RecyclerViewAdapter<Nothing, E, RecyclerView.ViewHolder>() {

    override var listener: Nothing? = null

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < list.size) {
            val item = holder as ViewHolder<E, Nothing>

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
}