package com.dupleit.demo.kotlintest.Network

import com.dupleit.apps.kotlintexting.Models.ServerData
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by mandeep on 9/11/17.
 */
interface ApiService {

    //@FormUrlEncoded
    @GET("index.php")
    fun getData(): Call<ServerData>

}