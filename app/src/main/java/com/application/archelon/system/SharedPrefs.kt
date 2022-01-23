package com.application.archelon.system

import android.content.Context
import android.content.SharedPreferences
import com.application.archelon.screens.BaseGeneralActivity

/**
 * SharedPreferences Manager
 * Because we want to avoid depending on a context each time we use this,
 * a singleton SharedPreferences object is used.
 */

const val USER_PREFERENCES_KEY = "com.achaelon.prefs"

// This key is used to maintain the authorization token for the current session.
const val USER_SESSION_TOKEN_KEY = "sessionToken";
// This key is used to store when the session was created, this can enable us to expire it "easily".
const val USER_SESSION_CREATION_TIMESTAMP_KEY = "sessionCreationTimestamp";

class SharedPrefs {

    // Assumes it has been initialized in the BaseGeneralActivity.
    private var prefs = BaseGeneralActivity.globalPreferences

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var userSessionAuthToken: String?
        get() = prefs!!.getString(USER_SESSION_TOKEN_KEY, null)
        set(value) = prefs!!.edit { it.putString(USER_SESSION_TOKEN_KEY, value) }

    var userSessionCreationTimestamp: Long
        get() = prefs!!.getLong(USER_SESSION_CREATION_TIMESTAMP_KEY, 0L)
        set(value) = prefs!!.edit { it.putLong(USER_SESSION_CREATION_TIMESTAMP_KEY, value) }

    fun clearSessionStoredData() {
        prefs!!.edit {
            it.remove(USER_SESSION_CREATION_TIMESTAMP_KEY)
            it.remove(USER_SESSION_TOKEN_KEY)
        }
    }

    companion object {
        private var instance: SharedPrefs? = null
        fun getSharedPreferencesForContext(context: Context): SharedPreferences? {
            return context.getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE)
        }

        fun getInstance(): SharedPrefs? {
            if (instance == null) {
                instance =
                    SharedPrefs()
            }
            return instance
        }
    }
}