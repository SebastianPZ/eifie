package com.mentalhealth.eifie.data.network.apidi

import com.mentalhealth.eifie.data.models.request.AppointmentRequest
import com.mentalhealth.eifie.data.models.request.LoginRequest
import com.mentalhealth.eifie.data.models.request.PatientRequest
import com.mentalhealth.eifie.data.models.request.PsychologistRequest
import com.mentalhealth.eifie.data.models.response.AppointmentRegisterResponse
import com.mentalhealth.eifie.data.models.response.AppointmentResponse
import com.mentalhealth.eifie.data.models.response.BaseResponse
import com.mentalhealth.eifie.data.models.response.HospitalResponse
import com.mentalhealth.eifie.data.models.response.PatientResponse
import com.mentalhealth.eifie.data.models.response.UserPatientResponse
import com.mentalhealth.eifie.data.models.response.ProfileResponse
import com.mentalhealth.eifie.data.models.response.UserPsychologistResponse
import com.mentalhealth.eifie.data.models.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST("user/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<BaseResponse<ProfileResponse>>

    @POST("user/patient")
    suspend fun registerPatient(
        @Body request: PatientRequest
    ): Response<BaseResponse<UserPatientResponse>>

    @POST("user/psychologist")
    suspend fun registerPsychologist(@Body request: PsychologistRequest): Response<BaseResponse<UserPsychologistResponse>>

    @Multipart
    @POST("user/profilePic")
    suspend fun registerProfilePicture(
        @Query("userId") userId: Long,
        @Part profilePic: MultipartBody.Part ?= null): Response<BaseResponse<UserResponse>>

    @GET("util/hospital")
    suspend fun getHospitals(): Response<BaseResponse<List<HospitalResponse>>>

    @GET("/appointment/findByPatient")
    suspend fun getAppointmentByPatient(
        @Header("Authorization") token: String,
        @Query("patientId") patientId: Long,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<BaseResponse<List<AppointmentResponse>>>

    @GET("/appointment/findByPsychologist")
    suspend fun getAppointmentByPsychologist(
        @Header("Authorization") token: String,
        @Query("psychologistId") psychologistId: Long,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<BaseResponse<List<AppointmentResponse>>>

    @POST("/appointment/saveAppointment")
    suspend fun saveAppointment(
        @Header("Authorization") token: String,
        @Body generateAppointmentRequestDTO: AppointmentRequest
    ): Response<BaseResponse<AppointmentRegisterResponse>>

    @GET("/psychologist/generateAccessCode")
    suspend fun generatePsychologistCode(
        @Header("Authorization") token: String,
        @Query("psychologistId") psychologistId: Long): Response<BaseResponse<String>>

    @GET("/patient/validateAccessCode")
    suspend fun validatePsychologistCode(
        @Header("Authorization") token: String,
        @Query("accessCode") code: String): Response<BaseResponse<UserPsychologistResponse>>

    @POST("/patient/psychologist")
    suspend fun assignPsychologist(
        @Query("patientId") patientId: Long,
        @Query("psychologistId") psychologistId: Long
    ): Response<BaseResponse<UserPatientResponse>>

    @GET("/psychologist/patients")
    suspend fun listPatientByPsychologist(
        @Header("Authorization") token: String,
        @Query("psychologistId") psychologistId: Long
    ): Response<BaseResponse<List<PatientResponse>>>

    @GET("/psychologist")
    suspend fun getPsychologistById(
        @Header("Authorization") token: String,
        @Query("psychologistId") psychologistId: Long
    ): Response<BaseResponse<UserPsychologistResponse>>

    @GET("/patient")
    suspend fun getPatientById(
        @Header("Authorization") token: String,
        @Query("patientId") patientId: Long
    ): Response<BaseResponse<UserPatientResponse>>
}