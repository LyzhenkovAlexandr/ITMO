package com.example.testvk.network

import androidx.paging.PagingSource
import com.example.testvk.network.model.Image

class ImageRepository(private val apiService: ApiService) {

    fun getImagesPagingSource(): PagingSource<Int, Image> = ImagePagingSource(apiService)
}
