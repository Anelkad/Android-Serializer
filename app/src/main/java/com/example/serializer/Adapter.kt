package com.example.serializer

import PolymorphicItem
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class Adapter: ListAdapter<PolymorphicItem, RecyclerView.ViewHolder>(
    DiffCallback()
) {
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PolymorphicItem.Person -> R.layout.item_person
            is PolymorphicItem.Car -> R.layout.item_car
            else -> throw IllegalStateException("Unknown view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_person -> {
                PersonViewHolder.create(
                    parent = parent
                )
            }
            else -> {
                CarViewHolder.create(parent)
            }
        }
    }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val listItem = getItem(position)
            listItem.let {
                when (listItem) {
                    is PolymorphicItem.Person -> {
                        val movieHolder = (holder as PersonViewHolder)
                        movieHolder.bind(listItem)
                    }

                    is PolymorphicItem.Car -> (holder as CarViewHolder).bind(listItem)
                    else -> {}
                }
            }
        }

        class DiffCallback : DiffUtil.ItemCallback<PolymorphicItem>() {
            override fun areItemsTheSame(
                oldItem: PolymorphicItem,
                newItem: PolymorphicItem
            ): Boolean {
                val isSameMovieItem = oldItem is PolymorphicItem.Person
                        && newItem is PolymorphicItem.Person
                        && oldItem.personName == newItem.personName

                val isSameAdItem = oldItem is PolymorphicItem.Car
                        && newItem is PolymorphicItem.Car
                        && oldItem.carName == newItem.carName

                return isSameMovieItem || isSameAdItem
            }

            override fun areContentsTheSame(oldItem: PolymorphicItem, newItem: PolymorphicItem) =
                oldItem == newItem
        }
    }