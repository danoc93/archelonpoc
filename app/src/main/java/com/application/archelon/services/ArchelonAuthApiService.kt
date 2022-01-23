package com.application.archelon.services

import com.application.archelon.models.SessionData

import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody

/**
 * ArchelonAuthApiService
 * Service for the authentication API
 */

private const val API_BASE_URL = "https://archaelon.roussos.mobi/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val rf = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(API_BASE_URL)
    .build()

interface ArchelonAuthApiService {
    @POST("login/")
    suspend fun login(@Body requestBody: RequestBody): Response<SessionData>
}

object ArchelonAuthApi {
    val service: ArchelonAuthApiService by lazy {
        rf.create(ArchelonAuthApiService::class.java)
    }
}