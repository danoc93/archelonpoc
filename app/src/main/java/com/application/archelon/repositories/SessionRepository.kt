package com.application.archelon.repositories

import android.util.Log
import com.application.archelon.services.ArchelonAuthApi
import com.application.archelon.models.SessionData
import com.application.archelon.system.SharedPrefs
import okhttp3.MultipartBody
import okhttp3.RequestBody


/**
 * SessionRepository
 * This is used to manage authentication and setup sessions.
 */

// Because the current Authentication system is quite simple, we have to implement a basic expiration mechanism.
// Ideally, this would be OAuth and we could use refresh tokens to maintain sessions, but not much we can do for now.
const val USER_SESSION_EXPIRY_DAYS = 15;
const val USER_SESSION_EXPIRY_MS = USER_SESSION_EXPIRY_DAYS * 86400000L;

enum class LoginState {
    Success,
    SuccessExistingSession,
    InvalidCredentials,
    ServerError,
    UnknownError,
    NotLoggedIn
}

object SessionRepository {

    /**
     * This function attempts to login a user and setup an internal session.
     * A code is returned, any consumer UI can deal with these codes accordingly.
     */
    suspend fun loginUser(
        username: String,
        password: String,
        sharedPreferences: SharedPrefs
    ): LoginState {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .build();
        try {
            val response = ArchelonAuthApi.service.login(requestBody)
            if (response.isSuccessful) {
                val responseContent = response.body();
                storeSessionDataInPreferences(responseContent, sharedPreferences)
                return LoginState.Success;
            }
            if (response.code() in 400..499) {
                return LoginState.InvalidCredentials;
            }
            response.errorBody()?.string()?.let { Log.e("[User Repository]", it) };
            return LoginState.ServerError;
        } catch (e: Exception) {
            Log.e("[User Repository]", "Error talking to server", e);
            return LoginState.UnknownError;
        }

    }

    /**
     * This function enables us to detect if there is a valid session.
     * With this we can avoid logging in every time.
     * A more complete implementation can leverage the AuthenticationManager as well as a proper OAuth workflow.
     * At the moment there is no support for an obvious expire/refresh system, so we can expire them on the device at fixed intervals.
     */
    fun getActiveSessionFromPreferences(sharedPreferences: SharedPrefs): SessionData? {
        val sessionCreationTimestamp = sharedPreferences.userSessionCreationTimestamp;
        val sessionToken = sharedPreferences.userSessionAuthToken;

        // Does it even exist?
        if (sessionToken === null || sessionCreationTimestamp == 0L) {
            Log.d("[User Repository]", "Session not found")
            return null
        }
        Log.d("[User Repository]", "Session found")
        // If it exists, has it expired?
        val currentTimeStamp = System.currentTimeMillis()
        val timeElapsed = currentTimeStamp - sessionCreationTimestamp
        if (timeElapsed > USER_SESSION_EXPIRY_MS) {
            // Clear existing data.
            Log.d("[User Repository]", "Expired session detected")
            clearExistingSession(sharedPreferences)
            return null
        }
        return SessionData(key = sessionToken)
    }

    /**
     * Clear the session data, useful for automatic expiry and log off operations.
     */
    fun clearExistingSession(sharedPreferences: SharedPrefs) {
        Log.d("[User Repository]", "Clearing session data")
        sharedPreferences.clearSessionStoredData();
    }

    /**
     * Simple way to store a token so we can keep the user in a logged in state.
     * Because Authentication is very basic SharedPreferences is enough.
     */
    private fun storeSessionDataInPreferences(
        tokenResponse: SessionData?,
        sharedPreferences: SharedPrefs
    ) {

        if (tokenResponse == null || tokenResponse.key.isEmpty()) {
            Log.w("[User Repository]", "Cannot store session key, empty value")
            return;
        }
        sharedPreferences.userSessionCreationTimestamp = System.currentTimeMillis();
        sharedPreferences.userSessionAuthToken = tokenResponse.key;
    }

}