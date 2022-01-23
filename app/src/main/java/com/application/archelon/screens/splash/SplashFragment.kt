package com.application.archelon.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.application.archelon.R
import kotlinx.android.synthetic.main.fragment_splash.view.*

class SplashFragment : Fragment() {

    private lateinit var viewLayout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadView(inflater.inflate(R.layout.fragment_splash, container, false))
        return viewLayout
    }

    private fun loadView(view: View) {
        viewLayout = view
        viewLayout.ivSplashLogo.setImageDrawable(
            ContextCompat.getDrawable(
                viewLayout.context,
                R.drawable.logo_archelon
            )
        )
    }

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }
}