package com.example.testvk.imagelist

import android.app.Application
import com.example.testvk.network.ApiService
import com.squareup.picasso.Picasso

class Application : Application() {
    private lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()

        apiService = ApiService.create(this)

        val picasso = Picasso.Builder(this)
            .build()

        Picasso.setSingletonInstance(picasso)
    }
}
