package com.eis.imagesearch.domain

import com.eis.imagesearch.domain.models.Image

interface ImageRepository
{
    suspend fun getListOfImages(query: String): List<Image>
}