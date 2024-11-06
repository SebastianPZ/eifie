package com.mentalhealth.eifie.di

import android.content.Context
import com.mentalhealth.eifie.data.network.apidi.ApiService
import com.mentalhealth.eifie.data.local.database.EDatabase
import com.mentalhealth.eifie.data.network.apiopenai.OpenAIService
import com.mentalhealth.eifie.data.local.preferences.EPreferences
import com.mentalhealth.eifie.data.network.DataAccess
import com.mentalhealth.eifie.data.repository.AppDefaultRepository
import com.mentalhealth.eifie.data.repository.AppointmentDefaultRepository
import com.mentalhealth.eifie.data.repository.AuthenticationDefaultRepository
import com.mentalhealth.eifie.data.repository.ChatDefaultRepository
import com.mentalhealth.eifie.data.repository.HospitalDefaultRepository
import com.mentalhealth.eifie.data.repository.MessageDefaultRepository
import com.mentalhealth.eifie.data.repository.NotificationDefaultRepository
import com.mentalhealth.eifie.data.repository.PatientDefaultRepository
import com.mentalhealth.eifie.data.repository.PsychologistDefaultRepository
import com.mentalhealth.eifie.data.repository.SupporterDefaultRepository
import com.mentalhealth.eifie.data.repository.SurveyDefaultRepository
import com.mentalhealth.eifie.data.repository.UserDefaultRepository
import com.mentalhealth.eifie.domain.repository.AppRepository
import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.domain.repository.ChatRepository
import com.mentalhealth.eifie.domain.repository.HospitalRepository
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.domain.repository.NotificationRepository
import com.mentalhealth.eifie.domain.repository.PatientRepository
import com.mentalhealth.eifie.domain.repository.PsychologistRepository
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import com.mentalhealth.eifie.domain.repository.SurveyRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesAuthenticationRepository(api: ApiService, preferences: EPreferences, dataAccess: DataAccess): AuthenticationRepository {
        return AuthenticationDefaultRepository(api = api, preferences = preferences, dataAccess = dataAccess)
    }

    @Provides
    fun providesHospitalRepository(api: ApiService, dataAccess: DataAccess): HospitalRepository {
        return HospitalDefaultRepository(api = api, dataAccess = dataAccess)
    }

    @Provides
    fun providesUserRepository(@ApplicationContext appContext: Context, api: ApiService, database: EDatabase, preferences: EPreferences, dataAccess: DataAccess): UserRepository {
        return UserDefaultRepository(context = appContext, api = api, database = database, preferences = preferences, dataAccess = dataAccess)
    }

    @Provides
    fun providesAppointmentRepository(api: ApiService, preferences: EPreferences, dataAccess: DataAccess): AppointmentRepository {
        return AppointmentDefaultRepository(api = api, preferences = preferences, dataAccess = dataAccess)
    }

    @Provides
    fun providesChatRepository(database: EDatabase, preferences: EPreferences): ChatRepository {
        return ChatDefaultRepository(database = database, preferences = preferences)
    }

    @Provides
    fun providesMessageRepository(database: EDatabase, api: OpenAIService, preferences: EPreferences): MessageRepository {
        return MessageDefaultRepository(database = database, api = api, preferences = preferences)
    }

    @Provides
    fun providesSupBotRepository(database: EDatabase, preferences: EPreferences): SupporterRepository {
        return SupporterDefaultRepository(database = database, preferences = preferences)
    }

    @Provides
    fun providesPsychologistRepository(api: ApiService, preferences: EPreferences, dataAccess: DataAccess): PsychologistRepository {
        return PsychologistDefaultRepository(api = api, preferences = preferences, dataAccess = dataAccess)
    }

    @Provides
    fun providesPatientRepository(api: ApiService, preferences: EPreferences, dataAccess: DataAccess): PatientRepository {
        return PatientDefaultRepository(api = api, preferences = preferences, dataAccess = dataAccess)
    }

    @Provides
    fun providesSurveyRepository(api: ApiService, preferences: EPreferences, dataAccess: DataAccess): SurveyRepository {
        return SurveyDefaultRepository(api = api, preferences = preferences, dataAccess = dataAccess)
    }

    @Provides
    fun providesNotificationRepository(
        @ApplicationContext context: Context,
        api: ApiService,
        preferences: EPreferences,
        database: EDatabase,
        dataAccess: DataAccess
    ): NotificationRepository {
        return NotificationDefaultRepository(
            context = context,
            api = api,
            preferences = preferences,
            database = database,
            dataAccess = dataAccess
        )
    }

    @Provides
    fun providesAppRepository(database: EDatabase): AppRepository {
        return AppDefaultRepository(database)
    }

}