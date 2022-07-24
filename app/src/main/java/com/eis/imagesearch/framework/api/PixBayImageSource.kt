package com.eis.imagesearch.framework.api

import com.eis.imagesearch.data.NetworkImageSource
import com.eis.imagesearch.data.NetworkResponse
import com.eis.imagesearch.domain.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder

class PixBayImageSource : NetworkImageSource
{
    private val api: PixBayApi
    private val mapper: PixBayImageMapper

    constructor(api: PixBayApi, mapper: PixBayImageMapper)
    {
        this.api = api
        this.mapper = mapper
    }

    override suspend fun getImages(query: String): NetworkResponse<List<Image>>
    {
        return withContext(Dispatchers.IO)
        {
            try
            {
                val encodedQuery = URLEncoder.encode(query, "utf-8")
                val result = api.getImages(encodedQuery)
                val apiImages = result.hits
                val images = apiImages.map { mapper.mapFrom(it) }

                NetworkResponse(success = true, data = images, error = null)
            }
            catch (exception: Exception)
            {
                exception.printStackTrace()
                NetworkResponse(success = false, data = null, error = exception.message)
            }
        }
    }
}