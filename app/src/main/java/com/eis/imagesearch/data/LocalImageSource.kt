package com.eis.imagesearch.data

import com.eis.imagesearch.domain.models.Image

interface LocalImageSource
{
    suspend fun getImages(query: String): List<Image>?
    suspend fun setImages(query: String, images: List<Image>)
}