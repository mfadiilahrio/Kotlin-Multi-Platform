package com.rio.kotlinmultiplatform.common

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder

abstract class RecyclerViewAdapter<T, E, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>(),
    ListPreloader.PreloadModelProvider<E> {

    abstract var listener: T?

    val items = mutableListOf<E>()

    val list: List<E> = items

    var loading = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    open fun setList(list: List<E>) {
        items.clear()
        items.addAll(list)

        notifyDataSetChanged()
    }

    open fun appendList(list: List<E>) {
        items.addAll(list)

        notifyDataSetChanged()
    }

    open fun appendList(position: Int, list: List<E>) {
        items.addAll(position, list)

        notifyDataSetChanged()
    }

    open fun appendItem(item: E) {
        val lastIndex = items.size

        items.add(item)

        notifyItemInserted(lastIndex)
    }

    open fun replaceItem(index: Int, newItem: E) {
        items[index] = newItem

        notifyItemChanged(index)
    }

    open fun replaceItem(oldItem: E, newItem: E) {
        val index = items.indexOf(oldItem)

        if (index > -1) {
            items[index] = newItem

            notifyItemChanged(index)
        }
    }

    open fun appendItem(position: Int, item: E, isAnimated: Boolean = true) {
        items.add(position, item)

        if (isAnimated) {
            notifyItemInserted(position)
        } else {
            notifyDataSetChanged()
        }
    }

    open fun removeItems(list: List<E>) {
        items.removeAll(list)

        notifyDataSetChanged()
    }

    open fun removeItem(item: E, isAnimated: Boolean) {
        var deletedIndex = 0

        for (selectedItem in items) {

            if (selectedItem === item) {
                break
            }

            deletedIndex++
        }

        items.remove(item)

        if (isAnimated) {
            notifyItemRemoved(deletedIndex)
        } else {
            notifyDataSetChanged()
        }
    }

    open fun removeItem(item: E) {
        removeItem(item, true)
    }

    @JvmOverloads
    open fun removeItem(position: Int, isAnimated: Boolean = true) {
        items.removeAt(position)

        if (isAnimated) {
            notifyItemRemoved(position)
        } else {
            notifyDataSetChanged()
        }
    }

    open fun removeRange(start: Int, end: Int) {
        items.subList(start, end).clear()

        notifyDataSetChanged()
    }

    open fun clearList() {
        items.clear()

        notifyDataSetChanged()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: VH, position: Int) {
        (holder as ViewHolder<E, *>).onBindViewHolder(list[position])
    }

    override fun getPreloadItems(position: Int): List<E> {
        return list.subList(position, position + 1)
    }

    override fun getPreloadRequestBuilder(item: E): RequestBuilder<*>? {
        return null
    }

    override fun getItemCount(): Int {
        return if (loading) {
            items.size + 1
        } else {
            items.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (loading && position >= items.size) {
            99
        } else {
            return 0
        }
    }
}