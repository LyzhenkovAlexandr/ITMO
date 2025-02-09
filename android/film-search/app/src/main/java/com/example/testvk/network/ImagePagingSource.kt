package com.example.testvk.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testvk.network.model.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImagePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> =
        withContext(Dispatchers.IO) {
            try {
                val nextPageNumber = params.key ?: 1
                val response = apiService.getMovies(nextPageNumber)

                return@withContext if (response.isSuccessful) {
                    val items = response.body()?.docs ?: emptyList()

                    LoadResult.Page(
                        data = items,
                        prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                        nextKey = if (items.isEmpty()) null else nextPageNumber + 1
                    )
                } else {
                    LoadResult.Error(Exception("Error fetching data"))
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition)
        return closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
    }
}
