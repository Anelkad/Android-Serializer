package com.example.serializer

import PolymorphicItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.serializer.databinding.ItemCarBinding

class CarViewHolder(
    private val itemCarBinding: ItemCarBinding,
) :
    RecyclerView.ViewHolder(itemCarBinding.root) {
    companion object {
        fun create(
            parent: ViewGroup,
            ): CarViewHolder {
            val binding = ItemCarBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
            return CarViewHolder(binding)
        }
    }

    fun bind(car: PolymorphicItem.Car) {
        itemCarBinding.tvName.text = car.carName
        itemCarBinding.tvModelAndColor.text = String.format("%s, %s",car.carModel, car.carColor)
        Glide
            .with(itemCarBinding.ivImage.context)
            .load(car.imageUrl)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.baseline_clear_24)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .into(itemCarBinding.ivImage)
    }
}