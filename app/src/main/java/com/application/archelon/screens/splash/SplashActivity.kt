package com.application.archelon.screens.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import com.application.archelon.screens.BaseGeneralActivity
import com.application.archelon.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_generic.*

class SplashActivity : BaseGeneralActivity() {
    override fun setToolBar() {
        toolbar.visibility = View.GONE
    }

    @SuppressLint("CheckResult")
    override fun loadFragment(callback: (f: Fragment) -> Unit) {
        callback.invoke(SplashFragment.newInstance())
        Handler(Looper.getMainLooper()).postDelayed({ closeSplashScreen() }, getSplashScreenDelay())
    }

    override fun loadTag(): String {
        return "SplashScreenTag"
    }

    private fun getSplashScreenDelay(): Long {
        return 2000L
    }

    private fun closeSplashScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}