package org.sopt.sample.di

import com.example.data.repository.AuthRepository
import com.example.data.repository.MainRepository
import com.example.data.repository.impl.AuthRepositoryImpl
import com.example.data.repository.impl.MainRepositoryImpl
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
    abstract fun providesAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun providesMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository
}
