package com.android.photoapp.domain

import com.android.photoapp.common.ResultWrapper
import com.android.photoapp.data.PhotosData
import com.android.photoapp.data.response.PhotosResponse
import kotlinx.coroutines.flow.Flow

interface IPhotosRepository {

    suspend fun getPhotos(
        page: Int,
        limit: Int,
        clientId: String
    ): ResultWrapper<ArrayList<PhotosResponse>>

    suspend fun insertPhotos(photosData: PhotosData)

    suspend fun getPhotosData(): Flow<List<PhotosData>>

    suspend fun deletePhotoTable()

}