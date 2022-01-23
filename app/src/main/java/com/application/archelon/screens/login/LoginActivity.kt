package com.application.archelon.screens.login

import android.view.View
import androidx.fragment.app.Fragment
import com.application.archelon.screens.BaseGeneralActivity
import kotlinx.android.synthetic.main.activity_generic.*

class LoginActivity : BaseGeneralActivity() {

    override fun setToolBar() {
        toolbar.visibility = View.GONE
    }

    override fun loadFragment(callback: (f: Fragment) -> Unit) {
        callback.invoke(LoginFragment.newInstance())
    }

    override fun loadTag(): String {
        return "LoginTag"
    }
}