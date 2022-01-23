package com.application.archelon.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.archelon.repositories.LoginState
import com.application.archelon.repositories.SessionRepository
import com.application.archelon.system.SharedPrefs
import kotlinx.coroutines.*

class LoginViewModel : ViewModel() {

    private val loginState = MutableLiveData<LoginState>()
    private val isLoginInProgress = MutableLiveData<Boolean>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        loginState.value = LoginState.NotLoggedIn;
        isLoginInProgress.value = false;
    }

    fun getLoginState(): MutableLiveData<LoginState> {
        return loginState
    }

    fun getIsLoginInProgress(): MutableLiveData<Boolean> {
        return isLoginInProgress
    }

    fun attemptLogin(username: String, password: String, sharedPreferences: SharedPrefs) {
        isLoginInProgress.value = true;
        coroutineScope.launch {
            val resultingLoginState =
                SessionRepository.loginUser(
                    username,
                    password,
                    sharedPreferences
                );
            loginState.value = resultingLoginState;
            isLoginInProgress.value = false;
        }
    }

    fun attemptToFindActiveSession(sharedPreferences: SharedPrefs) {
        val sessionFound = SessionRepository.getActiveSessionFromPreferences(sharedPreferences);
        if (sessionFound != null) {
            loginState.value = LoginState.SuccessExistingSession
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}