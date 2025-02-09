package com.example.testvk.imagelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.testvk.network.model.Image
import com.example.testvk.network.ImageRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ImageListViewModel(
    private val repository: ImageRepository
) : ViewModel() {

    private val _images = MutableLiveData<PagingData<Image>>()
    private var currentPagingSource: PagingSource<Int, Image>? = null
    val images: LiveData<PagingData<Image>> get() = _images

    init {
        fetchImages()
    }

    private fun fetchImages() {
        val pager = Pager(PagingConfig(pageSize = 10)) {
            currentPagingSource?.takeIf { it.invalid.not() }
                ?: repository.getImagesPagingSource().also {
                    currentPagingSource = it
                }
        }

        viewModelScope.launch {
            pager.flow.cachedIn(viewModelScope).collectLatest {
                _images.postValue(it)
            }
        }
    }

    fun retry() {
        currentPagingSource?.invalidate()
    }
}
