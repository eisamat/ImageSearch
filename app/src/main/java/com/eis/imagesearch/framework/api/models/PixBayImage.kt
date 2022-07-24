package com.eis.imagesearch.framework.api.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class PixBayImage(
    @SerialName("id") val id: Int,
    @SerialName("tags") val tags: String,
    @SerialName("previewURL") val thumbnail: String,
    @SerialName("largeImageURL") val image: String,
    @SerialName("downloads") val downloads: Int,
    @SerialName("likes") val likes: Int,
    @SerialName("comments") val comments: Int,
    @SerialName("user") val user: String
)
