package com.kenny.interrapidisimotest1.di

import com.kenny.interrapidisimotest1.data.repository.AuthRepositoryImpl
import com.kenny.interrapidisimotest1.data.repository.LocalidadRepositoryImpl
import com.kenny.interrapidisimotest1.data.repository.TablaRepositoryImpl
import com.kenny.interrapidisimotest1.data.repository.VersionRepositoryImpl
import com.kenny.interrapidisimotest1.domain.repository.AuthRepository
import com.kenny.interrapidisimotest1.domain.repository.LocalidadRepository
import com.kenny.interrapidisimotest1.domain.repository.TablaRepository
import com.kenny.interrapidisimotest1.domain.repository.VersionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTablaRepository(impl: TablaRepositoryImpl): TablaRepository

    @Binds
    @Singleton
    abstract fun bindLocalidadRepository(impl: LocalidadRepositoryImpl): LocalidadRepository

    @Binds
    @Singleton
    abstract fun bindVersionRepository(impl: VersionRepositoryImpl): VersionRepository
}