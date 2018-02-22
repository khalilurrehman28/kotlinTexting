package com.dupleit.demo.kotlintest.Network

/**
 * Created by mandeep on 6/7/17.
 */

import com.dupleit.apps.kotlintexting.Utils.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val BASE_URL = Constants.webrul.webUrl
    private var retrofit: Retrofit? = null

    val client: Retrofit?
        get() {
            if (retrofit == null) {
                val gson = GsonBuilder()
                        .setLenient()
                        .create()

                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit
        }
}