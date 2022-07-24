package com.eis.imagesearch.data

interface Mapper<I, O>
{
    fun mapFrom(input: I): O
}