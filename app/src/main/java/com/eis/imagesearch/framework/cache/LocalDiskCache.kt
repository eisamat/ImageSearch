package com.eis.imagesearch.framework.cache

import android.content.Context
import com.eis.imagesearch.data.LocalImageSource
import com.eis.imagesearch.domain.models.Image
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class LocalDiskCache : LocalImageSource
{
    private val context: Context

    constructor(@ApplicationContext context: Context)
    {
        this.context = context
    }

    override suspend fun getImages(query: String): List<Image>?
    {
        return withContext(Dispatchers.IO)
        {
            val path = context.cacheDir
            val file = File(path, query.replace("\\s".toRegex(), ""))

            if (file.exists())
            {
                val fos = FileInputStream(file)
                val os = ObjectInputStream(fos)

                val images = try
                {
                    os.readObject() as List<Image>
                }
                catch (e: Exception)
                {
                    null
                }

                os.close()
                fos.close()
                images
            }
            else
            {
                null
            }
        }
    }

    override suspend fun setImages(query: String, images: List<Image>)
    {
        withContext(Dispatchers.IO)
        {
            val path = context.cacheDir
            val file = File(path, query.replace("\\s".toRegex(), ""))
            val fos = FileOutputStream(file)
            val os = ObjectOutputStream(fos)
            os.writeObject(images)
            os.close()
            fos.close()
        }
    }
}