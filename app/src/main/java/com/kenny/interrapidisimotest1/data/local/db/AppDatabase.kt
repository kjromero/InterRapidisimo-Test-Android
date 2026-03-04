package com.kenny.interrapidisimotest1.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kenny.interrapidisimotest1.data.local.dao.TablaDao
import com.kenny.interrapidisimotest1.data.local.dao.UserDao
import com.kenny.interrapidisimotest1.data.local.entity.TablaEntity
import com.kenny.interrapidisimotest1.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, TablaEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tableDao(): TablaDao
}