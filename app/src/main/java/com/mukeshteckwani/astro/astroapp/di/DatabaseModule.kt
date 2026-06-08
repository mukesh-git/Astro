package com.mukeshteckwani.astro.astroapp.di

import android.content.Context
import androidx.room.Room
import com.mukeshteckwani.astro.astroapp.db.AppDatabase
import com.mukeshteckwani.astro.astroapp.db.FavouriteChannelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "astro_db"
        ).build()
    }

    @Provides
    fun provideFavouriteChannelDao(database: AppDatabase): FavouriteChannelDao {
        return database.favouriteChannelDao()
    }
}
