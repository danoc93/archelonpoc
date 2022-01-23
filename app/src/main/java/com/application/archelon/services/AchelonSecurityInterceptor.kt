package com.application.archelon.services

import com.application.archelon.system.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response

/**
 * AchelonSecurityInterceptor
 * Interceptor class for setting Authentication for every request
 */

class AchelonSecurityInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader(
                    "Authorization",
                    "Token ${SharedPrefs.getInstance()?.userSessionAuthToken}"
                )
                .build()
        )
    }

}