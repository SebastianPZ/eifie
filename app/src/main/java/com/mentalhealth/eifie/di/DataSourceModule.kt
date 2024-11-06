package com.mentalhealth.eifie.di

import android.content.Context
import androidx.room.Room
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.network.apiopenai.OpenAIService
import com.mentalhealth.eifie.data.local.preferences.EDefaultPreferences
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.network.DataAccess
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://moodminder-backend.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesOpenAIService(): OpenAIService {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/v1/chat/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenAIService::class.java)
    }

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): EDatabase {
        return Room.databaseBuilder(
            appContext,
            EDatabase::class.java, "database-name"
        ).build()
    }

    @Provides
    @Singleton
    fun providesPreferences(@ApplicationContext appContext: Context): EPreferences {
        return EDefaultPreferences(appContext)
    }

    @Provides
    @Singleton
    fun providesDataAccess(api: ApiService, preferences: EPreferences): DataAccess {
        return DataAccess(api = api, preferences = preferences)
    }

}