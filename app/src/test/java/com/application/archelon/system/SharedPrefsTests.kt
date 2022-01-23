package com.application.archelon.system

import android.app.Activity
import android.content.SharedPreferences
import com.application.archelon.screens.BaseGeneralActivity
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Test
import java.lang.NullPointerException

class SharedPrefsTests {




    @Test
    fun staticObjectHasDataAfterInit() {
        val ctx = Activity()
        BaseGeneralActivity.globalPreferences = SharedPrefs.getSharedPreferencesForContext(ctx)
        val sharedPrefs = SharedPrefs.getInstance()
        assertNotNull(sharedPrefs)
    }

}