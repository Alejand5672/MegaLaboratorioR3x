//Luis Alejandro Hernández Márquez (241424)
// Programación de plataformas moviles
// prof: Juan Carlos Durini

package com.example.labluishernandez.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AssetDao {

    @Query("SELECT * FROM assets")
    suspend fun getAll(): List<AssetEntity>

    @Query("SELECT * FROM assets WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): AssetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(assets: List<AssetEntity>)

    @Query("DELETE FROM assets")
    suspend fun clearAll()
}
