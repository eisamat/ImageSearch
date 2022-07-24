package com.eis.imagesearch.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eis.imagesearch.databinding.ViewImageItemBinding
import com.eis.imagesearch.domain.models.Image
import com.squareup.picasso.Picasso

class ImagesAdapter :
    ListAdapter<Image, ImageViewHolder>
{
    private val listener: ImagesAdapterListener

    constructor(listener: ImagesAdapterListener) : super(DiffCallback)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewImageItemBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int)
    {
        holder.bind(getItem(position), listener)
    }
}

class ImageViewHolder(private val binding: ViewImageItemBinding) :
    RecyclerView.ViewHolder(binding.root)
{
    fun bind(image: Image, listener: ImagesAdapterListener)
    {
        binding.username.text = image.userName
        binding.tags.text = image.tagsToString()

        Picasso.get()
            .load(image.thumbnail)
            .into(binding.thumbnail)

        binding.root.setOnClickListener { listener.onImageClicked(image) }
    }
}

object DiffCallback : ItemCallback<Image>()
{
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean
    {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean
    {
        return oldItem == newItem
    }
}

interface ImagesAdapterListener
{
    fun onImageClicked(image: Image)
}