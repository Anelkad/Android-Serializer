package com.example.serializer

import PolymorphicItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.serializer.databinding.ItemPersonBinding

class PersonViewHolder(
    private val itemPersonBinding: ItemPersonBinding,
) :
    RecyclerView.ViewHolder(itemPersonBinding.root) {
    companion object {
        fun create(
            parent: ViewGroup,
            ): PersonViewHolder {
            val binding = ItemPersonBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            )
            return PersonViewHolder(binding)
        }
    }

    fun bind(person: PolymorphicItem.Person) {
        itemPersonBinding.tvName.text = person.personName
        itemPersonBinding.tvAgeAndGender.text = String.format("%d, %s",person.personAge, person.personGender)
        Glide
            .with(itemPersonBinding.ivImage.context)
            .load(person.imageUrl)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.baseline_clear_24)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
            .into(itemPersonBinding.ivImage)
    }
}