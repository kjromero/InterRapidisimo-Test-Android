package com.kenny.interrapidisimotest1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val username: String,
    val identification: String,
    val name: String,
)