package com.mentalhealth.eifie.data.api

import com.mentalhealth.eifie.data.api.models.request.LoginRequest
import com.mentalhealth.eifie.data.api.models.request.PatientRequest
import com.mentalhealth.eifie.data.api.models.request.PsychologistRequest
import com.mentalhealth.eifie.data.api.models.response.AppointmentResponse
import com.mentalhealth.eifie.data.api.models.response.BaseResponse
import com.mentalhealth.eifie.data.api.models.response.HospitalResponse
import com.mentalhealth.eifie.data.api.models.response.PatientResponse
import com.mentalhealth.eifie.data.api.models.response.ProfileResponse
import com.mentalhealth.eifie.data.api.models.response.PsychologistResponse
import com.mentalhealth.eifie.data.api.models.response.UserResponse
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
        @Body request: PatientRequest): Response<BaseResponse<PatientResponse>>

    @POST("user/psychologist")
    suspend fun registerPsychologist(@Body request: PsychologistRequest): Response<BaseResponse<PsychologistResponse>>

    @Multipart
    @POST("user/profilePic")
    suspend fun registerProfilePicture(
        @Query("userId") userId: Int,
        @Part profilePic: MultipartBody.Part ?= null): Response<BaseResponse<UserResponse>>

    @GET("util/hospital")
    suspend fun getHospitals(): Response<BaseResponse<List<HospitalResponse>>>

    @GET("/appointment/findByPatient")
    suspend fun getAppointmentByPatient(
        @Header("Authorization") token: String,
        @Query("patientId") patientId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<BaseResponse<List<AppointmentResponse>>>

    @GET("/appointment/findByPsychologist")
    suspend fun getAppointmentByPsychologist(): Response<BaseResponse<List<AppointmentResponse>>>
}