package com.android.photoapp.network

import com.android.photoapp.data.response.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page")page: Int,
        @Query("per_page") perPage:Int,
        @Query("client_id") clientId: String
    ) : ArrayList<PhotosResponse>

}