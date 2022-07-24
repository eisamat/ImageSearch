package com.eis.imagesearch.data

import com.eis.imagesearch.domain.ImageRepository
import com.eis.imagesearch.domain.models.Image

class ImageRepositoryImpl : ImageRepository
{
    private val networkImageSource: NetworkImageSource
    private val localImageSource: LocalImageSource

    constructor(networkImageSource: NetworkImageSource, localImageSource: LocalImageSource)
    {
        this.networkImageSource = networkImageSource
        this.localImageSource = localImageSource
    }

    override suspend fun getListOfImages(query: String): List<Image>
    {
        val imagesFromCache = localImageSource.getImages(query)

        if (imagesFromCache == null)
        {
            val result = networkImageSource.getImages(query)

            if (result.success)
            {
                val data = result.getDataOrThrow()
                localImageSource.setImages(query, data)
                return data
            }
            else
            {
                return emptyList()
            }
        }
        else
        {
            return imagesFromCache
        }
    }
}