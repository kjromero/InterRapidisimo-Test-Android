package com.kenny.interrapidisimotest1.di

import android.content.Context
import androidx.room.Room
import com.kenny.interrapidisimotest1.data.local.dao.TablaDao
import com.kenny.interrapidisimotest1.data.local.dao.UserDao
import com.kenny.interrapidisimotest1.data.local.db.AppDatabase
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "interrapidisimo.db")
            .build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideTableDao(db: AppDatabase): TablaDao = db.tableDao()
}