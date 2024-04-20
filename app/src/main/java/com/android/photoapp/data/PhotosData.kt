package com.android.photoapp.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PHOTOS_TABLE")
data class PhotosData(
    @PrimaryKey(autoGenerate = true)
    val id: Int?= null,
    val generatedImage: Bitmap? = null
)