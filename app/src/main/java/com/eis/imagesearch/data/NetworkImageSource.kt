package com.eis.imagesearch.data

import com.eis.imagesearch.domain.models.Image

interface NetworkImageSource
{
    suspend fun getImages(query: String): NetworkResponse<List<Image>>
}