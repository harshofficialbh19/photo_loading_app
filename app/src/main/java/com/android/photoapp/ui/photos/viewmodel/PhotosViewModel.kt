package com.android.photoapp.ui.photos.viewmodel

import android.app.Application
import android.provider.ContactsContract.Contacts.Photo
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.photoapp.R
import com.android.photoapp.common.CommonUtils
import com.android.photoapp.common.Pagination
import com.android.photoapp.common.ResultWrapper
import com.android.photoapp.data.PhotosData
import com.android.photoapp.data.repository.PhotosRepository
import com.android.photoapp.data.response.PhotosResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    application: Application
) : AndroidViewModel(application) {

    // Mutable state to hold the UI state
    private val _state = mutableStateOf(PhotosState())
    val state: State<PhotosState> = _state

    // Channel to communicate events
    private val _channel = Channel<PhotoChannel>()
    val channel = _channel.receiveAsFlow()

    // Pagination helper
    val pagination: Pagination = Pagination()

    // Function to handle incoming events
    fun onEvent(event: PhotosEvents) {
        when (event) {
            is PhotosEvents.GetPhotos -> {
                viewModelScope.launch {
                    getPhotos(event.isFirstTimeLoaded)
                }
            }

            is PhotosEvents.GetPhotosFromDB -> {
                viewModelScope.launch {
                    getPhotosFromDB()
                }
            }

            else -> {
                // Handle other events if needed
            }
        }
    }

    // Function to fetch photos from the network
    private suspend fun getPhotos(isFirstTimeLoaded: Boolean) {
        if (isFirstTimeLoaded) {
            _state.value = _state.value.copy(isLoading = true)
            pagination.reset()
        } else {
            _state.value = _state.value.copy(isPaginationLoader = true)
        }

        val photosResponse =
            photosRepository.getPhotos(
                page = pagination.nextPage(),
                limit = Pagination.ITEMS_PER_PAGE_30,
                clientId = "w9iS7yn14VfdN6jtIuOGilVIvWgmEdJDErXeRPoV0dU"
            )

        when (photosResponse) {
            is ResultWrapper.GenericError -> {
                _state.value = _state.value.copy(isLoading = false, isPaginationLoader = false)
            }
            is ResultWrapper.NetworkError -> {
                _state.value = _state.value.copy(isLoading = false, isPaginationLoader = false)
            }
            is ResultWrapper.Success -> {
                photosResponse.value.let { response ->
                    if (!response.isNullOrEmpty()) {
                        if (pagination.canLoad(isFirstTimeLoaded, response.size)) {
                            pagination.setTotal(10000)
                        }
                        pagination.pageLoaded()
                        handleAndInsertResponseToDB(response)
                        _channel.send(PhotoChannel.OnSuccess)
                        _state.value = _state.value.copy(isLoading = false, isPaginationLoader = false)
                    }
                }
            }
        }
    }

    // Function to fetch photos from local database
    private suspend fun getPhotosFromDB() {
        photosRepository.getPhotosData().collect {
            _state.value = _state.value.copy(photosData = it as ArrayList)
        }
    }

    // Function to handle and insert fetched photos into the database
    private suspend fun handleAndInsertResponseToDB(response: ArrayList<PhotosResponse>) {
        response.forEachIndexed { index, photo ->
            // Load the image with a fallback URL if it's null
            val image = CommonUtils.loadUrl(
                photo.urls?.small ?: "",
            )
            // If image is not null, create a PhotosData object and insert it into the repository
            image?.let { generatedImage ->
                val photoData =
                    PhotosData(generatedImage = generatedImage)
                photosRepository.insertPhotos(photoData)
            }
        }
    }

}

// Sealed class to define channel events
sealed class PhotoChannel {
    object OnSuccess : PhotoChannel()
}


