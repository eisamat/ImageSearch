package com.eis.imagesearch.framework.api.models

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class PixBayResult(
    @SerialName("total") val totalResults: Int,
    @SerialName("totalHits")val totalHits: Int,
    @SerialName("hits") val hits: List<PixBayImage>
)
