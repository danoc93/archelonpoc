package com.application.archelon.viewmodels

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.application.archelon.repositories.LoginState
import com.application.archelon.screens.BaseGeneralActivity
import com.application.archelon.screens.login.LoginViewModel
import com.application.archelon.system.SharedPrefs
import com.application.archelon.system.USER_PREFERENCES_KEY
import com.application.archelon.test_utils.getOrAwaitValue
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(AndroidJUnit4::class)

class LoginViewModelTests {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun onExistingSession_shouldReturnSuccessState() {
        val loginViewModel = LoginViewModel()

        val prefs = context?.getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE)
        BaseGeneralActivity.globalPreferences = prefs

        SharedPrefs.getInstance()!!.userSessionAuthToken = "FAKETOKEN"
        SharedPrefs.getInstance()!!.userSessionCreationTimestamp = 9999999999999L

        loginViewModel.attemptToFindActiveSession(SharedPrefs.getInstance()!!)
        val loginState = loginViewModel.getLoginState().getOrAwaitValue()

        assertEquals(loginState, LoginState.SuccessExistingSession)
    }

    @Test
    fun onNoToken_shouldReturnFailState() {
        val loginViewModel = LoginViewModel()

        val prefs = context?.getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE)
        BaseGeneralActivity.globalPreferences = prefs

        SharedPrefs.getInstance()!!.userSessionAuthToken = null

        loginViewModel.attemptToFindActiveSession(SharedPrefs.getInstance()!!)
        val loginState = loginViewModel.getLoginState().getOrAwaitValue()

        assertEquals(LoginState.NotLoggedIn, loginState)
    }

    @Test
    fun onOldSession_shouldReturnFailState() {
        val loginViewModel = LoginViewModel()

        val prefs = context?.getSharedPreferences(USER_PREFERENCES_KEY, Context.MODE_PRIVATE)
        BaseGeneralActivity.globalPreferences = prefs

        SharedPrefs.getInstance()!!.userSessionAuthToken = "FAKETOKEN"
        SharedPrefs.getInstance()!!.userSessionCreationTimestamp = 0L

        loginViewModel.attemptToFindActiveSession(SharedPrefs.getInstance()!!)
        val loginState = loginViewModel.getLoginState().getOrAwaitValue()

        assertEquals(loginState, LoginState.NotLoggedIn)
    }
}