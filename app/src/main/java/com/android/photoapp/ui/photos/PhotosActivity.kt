package com.android.photoapp.ui.photos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.android.photoapp.R
import com.android.photoapp.data.repository.PhotosRepository
import com.android.photoapp.ui.theme.PhotoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhotosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhotoAppTheme {
                PhotosScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteRoomDatabase()
    }


    private fun deleteRoomDatabase() {
        val databaseFile = getDatabasePath(getString(R.string.photo_data_base))
        if (databaseFile.exists()) {
            databaseFile.delete()
        }
    }
}
