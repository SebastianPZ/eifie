package com.mentalhealth.eifie.data.di

import android.content.Context
import androidx.room.Room
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.data.database.EDatabase
import com.mentalhealth.eifie.data.network.apiopenai.OpenAIService
import com.mentalhealth.eifie.data.preferences.EDefaultPreferences
import com.mentalhealth.eifie.data.preferences.EPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataSourceProvider {

    @Provides
    fun providesApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://depresivoinador.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun providesOpenAIService(): OpenAIService {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/v1/chat/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenAIService::class.java)
    }

    @Provides
    fun providesAppDatabase(@ApplicationContext appContext: Context): EDatabase {
        return Room.databaseBuilder(
            appContext,
            EDatabase::class.java, "database-name"
        ).build()
    }

    @Provides
    fun providesPreferences(@ApplicationContext appContext: Context): EPreferences {
        return EDefaultPreferences(appContext)
    }

}