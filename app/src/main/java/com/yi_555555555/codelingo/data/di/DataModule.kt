package com.yi_555555555.codelingo.data.di

import android.content.Context
import androidx.room.Room
import com.yi_555555555.codelingo.data.repository.UserRepositoryImpl
import com.yi_555555555.codelingo.data.retrofit.UserApi
import com.yi_555555555.codelingo.data.room.UserDataBase
import com.yi_555555555.codelingo.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

  @Provides
  @Singleton
  fun providesOkHttpClient(): OkHttpClient {
    return OkHttpClient
      .Builder()
      .addInterceptor(
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
      )
      .build()
  }

  @Provides
  @Singleton
  fun providesUserRepository(
    userApi: UserApi,
    userDataBase: UserDataBase
  ): UserRepository {
    return UserRepositoryImpl(userApi, userDataBase)
  }

  @Provides
  @Singleton
  fun providesUserDataBase(
    @ApplicationContext context: Context
  ): UserDataBase {
    return Room.databaseBuilder(
      context = context,
      klass = UserDataBase::class.java,
      name = "user.db"
    ).build()
  }

  @Provides
  @Singleton
  fun providesRetrofit(
    client: OkHttpClient
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(REMOTE_CODE_LINGO_BASE_URL)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun providesUserApi(retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
  }
}

private const val REMOTE_CODE_LINGO_BASE_URL = "https://backend-codelingo.onrender.com/api/"