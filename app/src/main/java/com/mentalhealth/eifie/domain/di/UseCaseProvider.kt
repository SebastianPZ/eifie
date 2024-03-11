package com.mentalhealth.eifie.domain.di

import com.mentalhealth.eifie.domain.repository.AppointmentRepository
import com.mentalhealth.eifie.domain.repository.AuthenticationRepository
import com.mentalhealth.eifie.domain.repository.HospitalRepository
import com.mentalhealth.eifie.domain.repository.UserRepository
import com.mentalhealth.eifie.domain.usecases.GetMonthCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.GetUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.GetWeekCalendarUseCase
import com.mentalhealth.eifie.domain.usecases.ListAppointmentsUseCase
import com.mentalhealth.eifie.domain.usecases.ListHospitalsUseCase
import com.mentalhealth.eifie.domain.usecases.LoginUserUseCase
import com.mentalhealth.eifie.domain.usecases.LogoutUserUseCase
import com.mentalhealth.eifie.domain.usecases.RegisterPsychologistUseCase
import com.mentalhealth.eifie.domain.usecases.RegisterPatientUseCase
import com.mentalhealth.eifie.domain.usecases.SaveUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateUserInformationUseCase
import com.mentalhealth.eifie.domain.usecases.UpdateUserPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseProvider {

    @Provides
    fun providesLoginUserUseCase(repository: AuthenticationRepository): LoginUserUseCase {
        return LoginUserUseCase(repository = repository)
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
}