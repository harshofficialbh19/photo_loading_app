package com.android.photoapp.ui.photos.viewmodel

import com.android.photoapp.data.PhotosData
import com.android.photoapp.data.response.PhotosResponse

data class PhotosState(
    val isLoading: Boolean = false,
    val photosResponse: ArrayList<PhotosResponse>? = null,
    val photosData: ArrayList<PhotosData>? = null,
    val isPaginationLoader: Boolean = false
)