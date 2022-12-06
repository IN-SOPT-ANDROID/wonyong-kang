package org.sopt.sample.di

import com.example.data.repository.AuthRepository
import com.example.data.repository.MainRepository
import com.example.data.repository.impl.AuthRepositoryImpl
import com.example.data.repository.impl.MainRepositoryImpl
import com.example.data.repository.impl.MusicRepositoryImpl
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
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    @Singleton
    abstract fun bindsMusicRepository(musicRepositoryImpl: MusicRepositoryImpl)
}
