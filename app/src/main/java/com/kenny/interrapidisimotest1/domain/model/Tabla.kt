package com.kenny.interrapidisimotest1.domain.model

data class Tabla(
    val tableName: String,
    val primaryKey: String?,
    val fieldCount: Int?,
    val lastSyncDate: String?,
)