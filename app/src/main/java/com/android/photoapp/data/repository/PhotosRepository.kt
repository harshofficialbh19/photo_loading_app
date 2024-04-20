package com.android.photoapp.data.repository

import com.android.photoapp.common.ResultWrapper
import com.android.photoapp.common.safeApiCall
import com.android.photoapp.data.PhotosData
import com.android.photoapp.data.db.PhotoDao
import com.android.photoapp.data.response.PhotosResponse
import com.android.photoapp.domain.IPhotosRepository
import com.android.photoapp.network.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(
    private val apiService: APIService,
    private val photoDao: PhotoDao
) : IPhotosRepository {
    override suspend fun getPhotos(
        page: Int,
        limit: Int,
        clientId: String
    ): ResultWrapper<ArrayList<PhotosResponse>> = safeApiCall(
        Dispatchers.IO
    ) {
        apiService.getPhotos(page = page, perPage = limit, clientId = clientId)
    }

    override suspend fun insertPhotos(photosData: PhotosData) {
        photoDao.insertPhotos(photosData)
    }

    override suspend fun getPhotosData(): Flow<List<PhotosData>> {
        return flow { emit(photoDao.getPhotosData()) }.flowOn(Dispatchers.IO)
    }

    override suspend fun deletePhotoTable() {
        photoDao.deletePhotoTable()
    }


}