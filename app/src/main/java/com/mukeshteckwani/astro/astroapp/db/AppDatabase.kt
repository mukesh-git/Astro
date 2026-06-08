package com.mukeshteckwani.astro.astroapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mukeshteckwani.astro.astroapp.model.FavouriteChannelEntity

@Database(entities = [FavouriteChannelEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteChannelDao(): FavouriteChannelDao
}
