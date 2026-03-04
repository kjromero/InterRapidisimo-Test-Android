package com.kenny.interrapidisimotest1.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kenny.interrapidisimotest1.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}