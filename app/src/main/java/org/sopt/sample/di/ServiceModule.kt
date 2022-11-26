package org.sopt.sample.di

import com.example.data.service.AuthService
import com.example.data.service.MainService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.sample.di.type.RetrofitType.REQ_RES
import org.sopt.sample.di.type.RetrofitType.SOPT
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesAuthService(@Retrofit2(SOPT) retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesMainService(@Retrofit2(REQ_RES) retrofit: Retrofit): MainService =
        retrofit.create(MainService::class.java)
}
