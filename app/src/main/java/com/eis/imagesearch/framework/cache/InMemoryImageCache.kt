package com.eis.imagesearch.framework.cache

import com.eis.imagesearch.data.LocalImageSource
import com.eis.imagesearch.domain.models.Image

class InMemoryImageCache: LocalImageSource
{
    private val imagesCache: HashMap<String, List<Image>> = HashMap()

    override suspend fun getImages(query: String): List<Image>?
    {
        return imagesCache[query]
    }

    override suspend fun setImages(query: String, images: List<Image>)
    {
        imagesCache[query] = images
    }
}