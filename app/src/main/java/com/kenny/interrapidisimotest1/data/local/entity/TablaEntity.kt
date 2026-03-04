package com.kenny.interrapidisimotest1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tables_schema")
data class TablaEntity(
    @PrimaryKey val tableName: String,
    val description: String?,
    val keyFields: String?,
    val active: Boolean?,
    val serverName: String?,
    val databaseName: String?,
)