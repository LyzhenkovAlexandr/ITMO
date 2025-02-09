package com.example.testvk.network.model

data class ImageResponse(
    val docs: List<Image>
)

data class Image(
    val id: Int,
    val poster: Poster,
)

data class Poster(
    val url: String?,
)
