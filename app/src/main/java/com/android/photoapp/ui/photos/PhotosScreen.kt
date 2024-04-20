package com.android.photoapp.ui.photos

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.photoapp.R
import com.android.photoapp.common.CommonUtils
import com.android.photoapp.data.PhotosData
import com.android.photoapp.data.response.PhotosResponse
import com.android.photoapp.ui.features.CommonProgressBar
import com.android.photoapp.ui.photos.viewmodel.PhotoChannel
import com.android.photoapp.ui.photos.viewmodel.PhotosEvents
import com.android.photoapp.ui.photos.viewmodel.PhotosViewModel
import com.android.photoapp.ui.theme.AppBackgroundColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PhotosScreen(photosViewModel: PhotosViewModel = hiltViewModel()) {

    // LaunchedEffect to trigger the initial loading of photos
    LaunchedEffect(key1 = Unit) {
        photosViewModel.onEvent(PhotosEvents.GetPhotos(isFirstTimeLoaded = true))
    }

    // LaunchedEffect to observe changes in the channel and trigger actions accordingly
    LaunchedEffect(key1 = Unit) {
        photosViewModel.channel.collect { channel ->
            when (channel) {
                is PhotoChannel.OnSuccess -> {
                    // If photos are successfully loaded, get them from the local database
                    photosViewModel.onEvent(PhotosEvents.GetPhotosFromDB)
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppBackgroundColor
    ) {

        // Display a progress bar while loading
        if (photosViewModel.state.value.isLoading) {

            CommonProgressBar(modifier = Modifier.fillMaxSize())

        } else if (!photosViewModel.state.value.photosData.isNullOrEmpty()) {
            // If photos are loaded and not empty, display them in a grid

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp), // Add vertical spacing
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Add horizontal spacing
            ) {

                itemsIndexed(
                    photosViewModel.state.value.photosData ?: emptyList()
                ) { index: Int, item: PhotosData ->
                    // Display each photo item
                    PhotosItem(item)

                    // If reached the last item and more photos can be loaded, trigger loading more
                    if (index == photosViewModel.state.value.photosData!!.lastIndex &&
                        photosViewModel.pagination.canLoadMore(photosViewModel.state.value.photosData!!.size)
                    ) {
                        photosViewModel.onEvent(PhotosEvents.GetPhotos(isFirstTimeLoaded = false))
                    }
                }

                // Display a progress bar at the end to indicate loading more
                item {
                    CommonProgressBar(modifier = Modifier.fillMaxSize())
                }

            }

        } else {
            // If no photos are available, display a message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                Text(
                    text = stringResource(R.string.no_data_found),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PhotosItem(photosData: PhotosData) {

    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {

        // Display the image for each photo item
        AsyncImage(
            url = photosData.generatedImage,
            placeholder = R.drawable.placeholder_loading,
            contentDescription = null
        )

    }

}

@Composable
fun AsyncImage(
    url: Bitmap?,
    placeholder: Int,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {

    // Display the image when it's loaded, or display a placeholder
    if (url != null) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            bitmap = url.asImageBitmap(),
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    } else {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = placeholder),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )
    }
}



