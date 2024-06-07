package com.mentalhealth.eifie.di

import android.content.Context
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.network.apiopenai.OpenAIService
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.repository.AppointmentDefaultRepository
import com.mentalhealth.eifie.data.repository.AuthenticationDefaultRepository
import com.mentalhealth.eifie.data.repository.ChatDefaultRepository
import com.mentalhealth.eifie.data.repository.HospitalDefaultRepository
import com.mentalhealth.eifie.data.repository.MessageDefaultRepository
import com.mentalhealth.eifie.data.repository.PatientDefaultRepository
import com.mentalhealth.eifie.data.repository.PsychologistDefaultRepository
import com.mentalhealth.eifie.data.repository.SupBotDefaultRepository
import com.mentalhealth.eifie.data.repository.SurveyDefaultRepository
import com.mentalhealth.eifie.data.repository.UserDefaultRepository
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.domain.repository.ChatRepository
import com.mentalhealth.eifie.domain.repository.HospitalRepository
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.domain.repository.PatientRepository
import com.mentalhealth.eifie.domain.repository.PsychologistRepository
import com.mentalhealth.eifie.domain.repository.SupBotRepository
import com.mentalhealth.eifie.domain.repository.SurveyRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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

    @Provides
    fun providesAppointmentRepository(api: ApiService, preferences: EPreferences): AppointmentRepository {
        return AppointmentDefaultRepository(api = api, preferences = preferences)
    }

    @Provides
    fun providesChatRepository(database: EDatabase, preferences: EPreferences): ChatRepository {
        return ChatDefaultRepository(database = database, preferences = preferences)
    }

    @Provides
    fun providesMessageRepository(database: EDatabase, api: OpenAIService): MessageRepository {
        return MessageDefaultRepository(database = database, api = api)
    }

    @Provides
    fun providesSupBotRepository(database: EDatabase): SupBotRepository {
        return SupBotDefaultRepository(database = database)
    }

    @Provides
    fun providesPsychologistRepository(api: ApiService, preferences: EPreferences): PsychologistRepository {
        return PsychologistDefaultRepository(api = api, preferences = preferences)
    }

    @Provides
    fun providesPatientRepository(api: ApiService, preferences: EPreferences): PatientRepository {
        return PatientDefaultRepository(api = api, preferences = preferences)
    }

    @Provides
    fun providesSurveyRepository(api: ApiService, preferences: EPreferences): SurveyRepository {
        return SurveyDefaultRepository(api = api, preferences = preferences)
    }
}