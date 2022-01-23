package com.application.archelon.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.application.archelon.R
import com.application.archelon.screens.home.HomeActivity
import com.application.archelon.repositories.LoginState
import com.application.archelon.system.SharedPrefs
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var viewLayout: View
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPrefs;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prepareObservers()
        loadView(inflater.inflate(R.layout.fragment_login, container, false))
        return this.viewLayout
    }

    private fun loadView(view: View) {
        viewLayout = view
        sharedPreferences = SharedPrefs()
        initListeners()
        viewModel.attemptToFindActiveSession(sharedPreferences)
    }

    private fun prepareObservers() {
        viewModel.getIsLoginInProgress().observe(viewLifecycleOwner, Observer { isInProgress ->
            if (isInProgress) {
                showLoadingState();
            } else if (viewModel.getLoginState().value != LoginState.Success) {
                // This makes the UI not re-show the button before the next activity is loaded.
                hideLoadingState();
            }
        })

        viewModel.getLoginState().observe(viewLifecycleOwner, Observer { loginState ->
            when (loginState) {
                LoginState.UnknownError -> {
                    showMessage(R.string.error_login_unknown)
                }
                LoginState.InvalidCredentials -> {
                    showMessage(R.string.error_login_invalid_data)
                }
                LoginState.ServerError -> {
                    showMessage(R.string.error_login_server)
                }
                LoginState.SuccessExistingSession -> {
                    proceedToHome(R.string.success_welcome_back);
                }
                LoginState.Success -> {
                    proceedToHome(R.string.success_welcome);
                }
                else -> {
                    // This just means we are not logged in.
                }
            }
        })
    }

    private fun showLoadingState() {
        viewLayout.btnLogin.visibility = View.INVISIBLE;
        viewLayout.loginSpinner.visibility = View.VISIBLE;
    }

    private fun hideLoadingState() {
        viewLayout.btnLogin.visibility = View.VISIBLE;
        viewLayout.loginSpinner.visibility = View.INVISIBLE;
    }

    private fun initListeners() {
        viewLayout.btnLogin.setOnClickListener(this)
    }

    private fun proceedToHome(successLabelCode: Int) {
        Toast.makeText(
            viewLayout.context,
            viewLayout.context.getString(successLabelCode),
            Toast.LENGTH_SHORT
        )
            .show()
        startActivity(Intent(activity, HomeActivity::class.java))
    }

    /**
     * Helper to show a message. For simplicity this is now just a Toast.
     */
    private fun showMessage(stringResourceId: Int) {
        Toast.makeText(
            viewLayout.context,
            viewLayout.context.getString(stringResourceId),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun handleLoginButtonClick() {

        // Simple form validation.
        val username = viewLayout.inputUsername.editText?.text.toString().trim();
        val password = viewLayout.inputPassword.editText?.text.toString();
        if (username.isEmpty() || password.isEmpty()) {
            showMessage(R.string.error_login_data_not_provided)
            return;
        }
        viewModel.attemptLogin(username, password, sharedPreferences)
    }

    override fun onClick(v: View?) {
        when (v) {
            viewLayout.btnLogin -> handleLoginButtonClick()
        }
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}