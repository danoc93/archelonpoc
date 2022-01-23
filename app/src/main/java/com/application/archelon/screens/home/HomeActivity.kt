package com.application.archelon.screens.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.application.archelon.BuildConfig
import com.application.archelon.R
import com.application.archelon.screens.splash.SplashActivity
import com.application.archelon.extensions.replaceFragment
import com.application.archelon.repositories.SessionRepository.clearExistingSession
import com.application.archelon.system.SharedPrefs
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * HomeActivity
 * Activity for the Home section. Here we allow users to start new surveys, or log out.
 */

class HomeActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPrefs by lazy { SharedPrefs() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        loadHomeFrame()
    }

    @SuppressLint("CheckResult")
    private fun loadHomeFrame() {
        replaceFragment(
            HomeFragment.newInstance(),
            R.id.main_container
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //React to actions on the context menu of the top bar.
        when (item.itemId) {
            R.id.action_logout -> showConfirmationForLogout()
            R.id.action_about -> showAboutDialog()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showConfirmationForLogout() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.logout_confirmation_message)
            .setCancelable(false)
            .setPositiveButton(
                R.string.logout_confirmation_text
            ) { _, _ -> handleLogoutAction() }
            .setNegativeButton(
                R.string.logout_cancel_text
            ) { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun showAboutDialog() {

        val details = this.getString(R.string.about_string).format(BuildConfig.VERSION_NAME)
        val appName = this.getString(R.string.app_name);

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(details)
            .setTitle(appName)
            .setCancelable(true)
            .setNegativeButton(
                R.string.about_cancel
            ) { dialog, _ -> dialog.cancel() }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun handleLogoutAction() {
        clearExistingSession(sharedPreferences)
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}
