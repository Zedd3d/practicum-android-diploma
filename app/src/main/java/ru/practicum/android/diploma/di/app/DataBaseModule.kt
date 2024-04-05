package ru.practicum.android.diploma.di.app

import androidx.room.Room
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.ApiEndpoints
import ru.practicum.android.diploma.data.network.HeadHunterService
import ru.practicum.android.diploma.domain.impl.VacanciesRepository
import ru.practicum.android.diploma.domain.impl.VacanciesRepositoryImpl


@Module
object DataBaseModule {

    @Provides
    fun provideDatabase(impl: AppDatabase): AppDatabase = Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
        .build()

    @Provides
    fun provideHeadHunterService(retrofit: Retrofit): HeadHunterService = retrofit.create(HeadHunterService::class.java)

    Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
    .build()

}
