package com.mentalhealth.eifie.di

import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.domain.repository.ChatRepository
import com.mentalhealth.eifie.domain.repository.HospitalRepository
import com.mentalhealth.eifie.domain.repository.MessageRepository
import com.mentalhealth.eifie.domain.repository.PatientRepository
import com.mentalhealth.eifie.domain.repository.PsychologistRepository
import com.mentalhealth.eifie.domain.repository.SupporterRepository
import com.mentalhealth.eifie.domain.repository.SurveyRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import com.mentalhealth.eifie.domain.usecases.AssignPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.GeneratePsychologistCodeUseCase
import com.mentalhealth.eifie.domain.usecases.GetChatMessagesUseCase
import com.mentalhealth.eifie.domain.usecases.GetFormDataUseCase
import com.mentalhealth.eifie.domain.usecases.GetNotificationsUseCase
import com.mentalhealth.eifie.domain.usecases.GetSurveyQuestionsUseCase
import com.mentalhealth.eifie.domain.usecases.GetMonthCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.GetPatientUseCase
import com.mentalhealth.eifie.domain.usecases.GetPatientsUseCase
import com.mentalhealth.eifie.domain.usecases.GetPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.GetUserChatsUseCase
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.GetWeekCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.ListAppointmentsUseCase
import com.mentalhealth.eifie.domain.usecases.ListHospitalsUseCase
import com.mentalhealth.eifie.domain.usecases.LoginUseCase
import com.mentalhealth.eifie.domain.usecases.LogoutUserUseCase
import com.mentalhealth.eifie.domain.usecases.RegisterPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.RegisterPatientUseCase
import com.mentalhealth.eifie.domain.usecases.RetrieveSupporterUseCase
import com.mentalhealth.eifie.domain.usecases.SaveChatUseCase
import com.mentalhealth.eifie.domain.usecases.SendMessageUseCase
import com.mentalhealth.eifie.domain.usecases.SaveUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.ScheduleAppointmentUseCase
import com.mentalhealth.eifie.domain.usecases.SearchPatientUseCase
import com.mentalhealth.eifie.domain.usecases.SendSurveyAnswersUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateSupporterNameUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateUserPhotoUseCase
import com.mentalhealth.eifie.domain.usecases.ValidateAssignCodeUseCase
import com.mentalhealth.eifie.domain.usecases.ValidateSurveyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun providesLoginUserUseCase(repository: AuthenticationRepository): LoginUseCase {
        return LoginUseCase(repository = repository)
    }

    @Provides
    fun providesLogoutUserUseCase(repository: UserRepository): LogoutUserUseCase {
        return LogoutUserUseCase(repository = repository)
    }

    @Provides
    fun providesRegisterUserUseCase(repository: AuthenticationRepository): RegisterPatientUseCase {
        return RegisterPatientUseCase(repository = repository)
    }

    @Provides
    fun providesRegisterPsychologistUseCase(repository: AuthenticationRepository): RegisterPsychologistUseCase {
        return RegisterPsychologistUseCase(repository = repository)
    }


    @Provides
    fun providesListHospitalsUseCase(repository: HospitalRepository): ListHospitalsUseCase {
        return ListHospitalsUseCase(repository = repository)
    }

    @Provides
    fun providesSaveUserUseCase(repository: UserRepository): SaveUserInformationUseCase {
        return SaveUserInformationUseCase(repository = repository)
    }

    @Provides
    fun providesUpdateUserUseCase(repository: UserRepository): UpdateUserInformationUseCase {
        return UpdateUserInformationUseCase(repository = repository)
    }

    @Provides
    fun providesGetUserUseCase(repository: UserRepository): GetUserInformationUseCase {
        return GetUserInformationUseCase(repository = repository)
    }


    @Provides
    fun providesUpdateUserPhotoUseCase(repository: UserRepository): UpdateUserPhotoUseCase {
        return UpdateUserPhotoUseCase(repository = repository)
    }

    @Provides
    fun providesGetMonthCalendarUseCase(): GetMonthCalendarUseCase {
        return GetMonthCalendarUseCase()
    }

    @Provides
    fun providesGetWeekCalendarUseCase(): GetWeekCalendarUseCase {
        return GetWeekCalendarUseCase()
    }

    @Provides
    fun providesListAppointmentsUseCase(
        appointmentRepository: AppointmentRepository,
        userRepository: UserRepository): ListAppointmentsUseCase {
        return ListAppointmentsUseCase(
            appointmentRepository = appointmentRepository,
            userRepository = userRepository)
    }

    @Provides
    fun providesScheduleAppointmentUseCase(
        appointmentRepository: AppointmentRepository,
        userRepository: UserRepository): ScheduleAppointmentUseCase {
        return ScheduleAppointmentUseCase(
            appointmentRepository = appointmentRepository,
            userRepository = userRepository
        )
    }

    @Provides
    fun providesValidatePsychologistCodeUseCase(
        repository: AuthenticationRepository): ValidateAssignCodeUseCase {
        return ValidateAssignCodeUseCase(repository = repository)
    }

    @Provides
    fun providesAssignPsychologistUseCase(
        repository: AuthenticationRepository,
        userRepository: UserRepository
        ): AssignPsychologistUseCase {
        return AssignPsychologistUseCase(repository = repository, userRepository = userRepository)
    }

    @Provides
    fun providesGetFormListUseCase(): GetNotificationsUseCase {
        return GetNotificationsUseCase()
    }

    @Provides
    fun providesGetFormQuestionsUseCase(repository: SurveyRepository): GetSurveyQuestionsUseCase {
        return GetSurveyQuestionsUseCase(repository = repository)
    }

    @Provides
    fun providesGetFormDataUseCase(): GetFormDataUseCase {
        return GetFormDataUseCase()
    }

    @Provides
    fun providesGeneratePsychologistCodeUseCase(
        repository: AuthenticationRepository): GeneratePsychologistCodeUseCase {
        return GeneratePsychologistCodeUseCase(repository = repository)
    }

    @Provides
    fun providesGetUserChatsUseCase(
        repository: ChatRepository): GetUserChatsUseCase {
        return GetUserChatsUseCase(chatRepository = repository)
    }

    @Provides
    fun providesGetChatMessagesUseCase(
        repository: MessageRepository): GetChatMessagesUseCase {
        return GetChatMessagesUseCase(messageRepository = repository)
    }

    @Provides
    fun providesSendMessagesUseCase(
        repository: MessageRepository
    ): SendMessageUseCase {
        return SendMessageUseCase(messageRepository = repository)
    }

    @Provides
    fun providesGetPatientsUseCase(
        userRepository: UserRepository,
        psychologistRepository: PsychologistRepository
    ): GetPatientsUseCase {
        return GetPatientsUseCase(
            userRepository = userRepository,
            psychologistRepository = psychologistRepository
        )
    }

    @Provides
    fun providesGetPsychologistUseCase(
        psychologistRepository: PsychologistRepository): GetPsychologistUseCase {
        return GetPsychologistUseCase(psychologistRepository = psychologistRepository)
    }

    @Provides
    fun providesGetPatientUseCase(
        patientRepository: PatientRepository): GetPatientUseCase {
        return GetPatientUseCase(patientRepository = patientRepository)
    }

    @Provides
    fun providesSearchPatientUseCase(
        patientRepository: PatientRepository): SearchPatientUseCase {
        return SearchPatientUseCase(patientRepository = patientRepository)
    }

    @Provides
    fun providesRetrieveSupporterUseCase(
        supporterRepository: SupporterRepository,
        userRepository: UserRepository): RetrieveSupporterUseCase {
        return RetrieveSupporterUseCase(supporterRepository = supporterRepository, userRepository)
    }

    @Provides
    fun providesUpdateSupporterUseCase(
        supporterRepository: SupporterRepository,
        userRepository: UserRepository
    ): UpdateSupporterNameUseCase {
        return UpdateSupporterNameUseCase(repository = supporterRepository, userRepository = userRepository)
    }

    @Provides
    fun providesSendSurveyAnswersUseCase(
        surveyRepository: SurveyRepository,
        userRepository: UserRepository): SendSurveyAnswersUseCase {
        return SendSurveyAnswersUseCase(surveyRepository = surveyRepository, userRepository = userRepository)
    }

    @Provides
    fun providesValidateSurveyUseCase(
        surveyRepository: SurveyRepository,
        userRepository: UserRepository): ValidateSurveyUseCase {
        return ValidateSurveyUseCase(surveyRepository = surveyRepository, userRepository = userRepository)
    }

    @Provides
    fun providesSaveChatUseCase(
        repository: ChatRepository,
        messageRepository: MessageRepository
    ): SaveChatUseCase {
        return SaveChatUseCase(repository = repository, messageRepository = messageRepository)
    }
}