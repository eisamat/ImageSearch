package com.eis.imagesearch.framework.api

import com.eis.imagesearch.BuildConfig
import com.eis.imagesearch.framework.api.models.PixBayResult
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Put the api key from local.properties
 * PIXBAY_APIKEY="XXXX"
 *
 * TODO: Pagination. For now, just get 100 results.
 */

interface PixBayApi
{
    @GET("api/")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("per_page") resultPerPage: Int = 100,
        @Query("key") key: String = BuildConfig.PIXBAY_APIKEY
    ): PixBayResult
}