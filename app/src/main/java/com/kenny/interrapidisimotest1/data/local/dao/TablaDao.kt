package com.kenny.interrapidisimotest1.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kenny.interrapidisimotest1.data.local.entity.TablaEntity

@Dao
interface TablaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(tables: List<TablaEntity>)

    @Query("SELECT * FROM tables_schema ORDER BY tableName ASC")
    suspend fun getAll(): List<TablaEntity>

    @Query("DELETE FROM tables_schema")
    suspend fun deleteAll()
}