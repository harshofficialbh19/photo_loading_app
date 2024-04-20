package com.android.photoapp.di

import android.content.Context
import androidx.room.Room
import com.android.photoapp.R
import com.android.photoapp.data.db.PhotoDao
import com.android.photoapp.data.db.PhotosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideLifeNowDatabase(@ApplicationContext appContext: Context): PhotosDatabase {
        return Room.databaseBuilder(
            appContext,
            PhotosDatabase::class.java,
            appContext.getString(R.string.photo_data_base)
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePhotosDao(liftNowDatabase: PhotosDatabase): PhotoDao = liftNowDatabase.photoDao()

}