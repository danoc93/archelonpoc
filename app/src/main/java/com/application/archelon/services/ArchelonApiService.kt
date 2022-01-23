package com.application.archelon.services

import com.application.archelon.models.Nest
import com.application.archelon.models.Survey

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

/**
 * ArchelonApiService
 * Service for the data API
 * This includes a special client which intercepts the requests to add the security token.
 */

private const val API_BASE_URL = "https://archaelon.roussos.mobi/api/archelon/"

interface ArchelonApiService {
    @GET("nest_list")
    suspend fun getNestList(): List<Nest>

    @POST("morning_survey")
    suspend fun saveMorningSurvey(@Body requestBody: Survey): Response<ResponseBody>
}

object ArchelonApi {
    val service: ArchelonApiService by lazy {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        // Use this client so that we can automatically include the token in the requests!
        val client = OkHttpClient.Builder()
            .addInterceptor(AchelonSecurityInterceptor())
            .build()

        val rf = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(API_BASE_URL)
            .client(client)
            .build()

        rf.create(ArchelonApiService::class.java)

    }
}