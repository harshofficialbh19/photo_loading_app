package com.android.photoapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.photoapp.data.PhotosData


@Dao
interface PhotoDao {

    @Insert
    suspend fun insertPhotos(photosData: PhotosData)

    @Query("SELECT * FROM PHOTOS_TABLE")
    suspend fun getPhotosData(): List<PhotosData>

    @Query("DELETE FROM PHOTOS_TABLE")
    suspend fun deletePhotoTable()
}