package com.mukeshteckwani.astro.astroapp.db

import androidx.room.*
import com.mukeshteckwani.astro.astroapp.model.FavouriteChannelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteChannelDao {
    @Query("SELECT * FROM favourite_channels")
    fun getAllFavourites(): Flow<List<FavouriteChannelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(channel: FavouriteChannelEntity)

    @Query("DELETE FROM favourite_channels WHERE channelId = :channelId")
    suspend fun deleteById(channelId: Int)
}
