package com.eis.imagesearch.domain.models

data class Image(
    val id: Int,
    val tags: List<Tag>,
    val thumbnail: String,
    val image: String,
    val userName: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
): java.io.Serializable
{
    fun tagsToString(): String
    {
        val tagsToString = tags
            .map { it.name }
            .map { tag -> tag.replaceFirstChar { it.uppercase() } }

        return tagsToString.joinToString(separator = ",")
    }
}
