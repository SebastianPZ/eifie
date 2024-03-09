package com.mentalhealth.eifie.data.di

import android.content.Context
import com.mentalhealth.eifie.data.api.ApiService
import com.mentalhealth.eifie.data.database.EDatabase
import com.mentalhealth.eifie.data.preferences.EPreferences
import com.mentalhealth.eifie.data.repository.AuthenticationDefaultRepository
import com.mentalhealth.eifie.data.repository.HospitalDefaultRepository
import com.mentalhealth.eifie.data.repository.UserDefaultRepository
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.domain.repository.HospitalRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Provides
    fun providesAuthenticationRepository(api: ApiService, preferences: EPreferences): AuthenticationRepository {
        return AuthenticationDefaultRepository(api = api, preferences = preferences)
    }

    @Provides
    fun providesHospitalRepository(api: ApiService): HospitalRepository {
        return HospitalDefaultRepository(api = api)
    }

    @Provides
    fun providesUserRepository(@ApplicationContext appContext: Context, api: ApiService, database: EDatabase, preferences: EPreferences): UserRepository {
        return UserDefaultRepository(context = appContext, api = api, database = database, preferences = preferences)
    }

}