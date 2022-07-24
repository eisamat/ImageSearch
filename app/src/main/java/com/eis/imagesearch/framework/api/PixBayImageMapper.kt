package com.eis.imagesearch.framework.api

import com.eis.imagesearch.data.Mapper
import com.eis.imagesearch.domain.models.Image
import com.eis.imagesearch.domain.models.Tag
import com.eis.imagesearch.framework.api.models.PixBayImage

class PixBayImageMapper: Mapper<PixBayImage, Image>
{
    override fun mapFrom(input: PixBayImage): Image
    {
        val tags = input.tags.split(",").map { Tag(it) }

        return Image(
            input.id,
            tags,
            input.thumbnail,
            input.image,
            input.user,
            input.likes,
            input.downloads,
            input.comments
        )
    }
}