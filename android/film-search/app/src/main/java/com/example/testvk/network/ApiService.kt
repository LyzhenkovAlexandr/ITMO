package com.example.testvk.network

import android.app.Application
import android.net.ConnectivityManager
import com.example.testvk.network.model.ImageResponse
import okhttp3.Interceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File

interface ApiService {

    @GET("v1.4/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
        @Query("sortField") sortField: String = "votes.kp",
        @Query("sortField") sortField1: String = "rating.kp",
        @Query("sortType") sortType: Int = -1,
        @Query("sortType") sortType1: Int = -1,
        @Query("token") apiKey: String = TOKEN
    ): Response<ImageResponse>

    companion object {
        private const val TOKEN = "QGRWXGN-7QJ45YC-GNG290M-1NAY4YT"
        private const val MB_200 = 200L * 1024 * 1024

        fun create(application: Application): ApiService {
            val cacheDir = File(application.cacheDir, "http_cache")
            val cache = okhttp3.Cache(cacheDir, MB_200)

            val cacheInterceptor = Interceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(application)) {
                    request.newBuilder()
                        .header("Cache-Control", "public, max-age=5")
                        .build()
                } else {
                    request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=604800")
                        .build()
                }
                chain.proceed(request)
            }

            val client = okhttp3.OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(cacheInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.kinopoisk.dev/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        private fun hasNetwork(application: Application): Boolean {
            val connectivityManager =
                application.getSystemService(Application.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}
