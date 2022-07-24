package com.eis.imagesearch.data

data class NetworkResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: String?
)
{
    fun getDataOrThrow(): T
    {
        return data ?: error("Data is null")
    }

    fun getErrorOrThrow(): String
    {
        return error ?: error("Data is null")
    }
}
