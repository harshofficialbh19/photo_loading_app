package com.android.photoapp.ui.photos.viewmodel

sealed class PhotosEvents {

    data class GetPhotos(val isFirstTimeLoaded: Boolean) : PhotosEvents()
    object GetPhotosFromDB : PhotosEvents()
}