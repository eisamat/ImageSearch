package com.eis.imagesearch.framework.dagger

import android.content.Context
import com.eis.imagesearch.data.ImageRepositoryImpl
import com.eis.imagesearch.data.LocalImageSource
import com.eis.imagesearch.data.NetworkImageSource
import com.eis.imagesearch.domain.ImageRepository
import com.eis.imagesearch.framework.api.PixBayImageMapper
import com.eis.imagesearch.framework.api.PixBayApi
import com.eis.imagesearch.framework.api.PixBayImageSource
import com.eis.imagesearch.framework.cache.InMemoryImageCache
import com.eis.imagesearch.framework.cache.LocalDiskCache
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule
{
    @Singleton
    @Provides
    fun providesPixBayApi(): PixBayApi
    {
        val contentType = "application/json".toMediaType()

        val json = Json { ignoreUnknownKeys = true }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://pixabay.com")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        return retrofit.create(PixBayApi::class.java)
    }

    @Provides
    fun providesImageMapper(): PixBayImageMapper
    {
        return PixBayImageMapper()
    }

    @Singleton
    @Provides
    fun providesLocalImageSource(@ApplicationContext context: Context): LocalImageSource
    {
        return LocalDiskCache(context)
    }

    @Singleton
    @Provides
    fun providesNetworkImageSource(api: PixBayApi, mapper: PixBayImageMapper): NetworkImageSource
    {
        return PixBayImageSource(
            api,
            mapper
        )
    }

    @Singleton
    @Provides
    fun providesImageRepository(
        networkImageSource: NetworkImageSource,
        localImageSource: LocalImageSource
    ): ImageRepository
    {
        return ImageRepositoryImpl(
            networkImageSource,
            localImageSource
        )
    }
}