package com.android.photoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.android.photoapp.common.BitmapConverter
import com.android.photoapp.data.PhotosData

@Database(
    entities = [PhotosData::class], version = 1, exportSchema = false
)
@TypeConverters(BitmapConverter::class)
abstract class PhotosDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}