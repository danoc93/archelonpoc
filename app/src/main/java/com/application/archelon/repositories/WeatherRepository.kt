package com.application.archelon.repositories

import android.content.Context
import com.application.archelon.R
import com.application.archelon.models.SimpleItem

/**
 * WeatherRepository
 * This is used to manage the weather data options.
 */

object WeatherRepository {
    fun getSkyOptions(context: Context): List<SimpleItem> {
        return listOf(
            SimpleItem(id = 1, name = context.getString(R.string.weather_sky_clear)),
            SimpleItem(id = 2, name = context.getString(R.string.weather_sky_lightovercast)),
            SimpleItem(id = 3, name = context.getString(R.string.weather_sky_heavyovercast)),
            SimpleItem(id = 4, name = context.getString(R.string.weather_sky_completelyovercast))
        )
    }

    fun getPrecipitationOptions(context: Context): List<SimpleItem> {
        return listOf(
            SimpleItem(id = 1, name = context.getString(R.string.weather_precipitation_none)),
            SimpleItem(id = 2, name = context.getString(R.string.weather_precipitation_drizzle)),
            SimpleItem(id = 3, name = context.getString(R.string.weather_precipitation_rain)),
            SimpleItem(id = 4, name = context.getString(R.string.weather_precipitation_heavyrain)),
            SimpleItem(id = 5, name = context.getString(R.string.weather_precipitation_storm))
        )
    }

    fun getWindIntensityOptions(context: Context): List<SimpleItem> {
        return listOf(
            SimpleItem(id = 1, name = context.getString(R.string.weather_windintensity_nowind)),
            SimpleItem(id = 2, name = context.getString(R.string.weather_windintensity_weak)),
            SimpleItem(id = 3, name = context.getString(R.string.weather_windintensity_medium)),
            SimpleItem(id = 4, name = context.getString(R.string.weather_windintensity_strong)),
            SimpleItem(id = 5, name = context.getString(R.string.weather_windintensity_gale))
        )
    }

    fun getWindDirectionOptions(context: Context): List<SimpleItem> {
        return listOf(
            SimpleItem(id = 1, name = context.getString(R.string.weather_winddirection_n)),
            SimpleItem(id = 2, name = context.getString(R.string.weather_winddirection_ne)),
            SimpleItem(id = 3, name = context.getString(R.string.weather_winddirection_e)),
            SimpleItem(id = 4, name = context.getString(R.string.weather_winddirection_se)),
            SimpleItem(id = 5, name = context.getString(R.string.weather_winddirection_s)),
            SimpleItem(id = 6, name = context.getString(R.string.weather_winddirection_sw)),
            SimpleItem(id = 7, name = context.getString(R.string.weather_winddirection_w)),
            SimpleItem(id = 8, name = context.getString(R.string.weather_winddirection_nw))
        )
    }

    fun getSurfOptions(context: Context): List<SimpleItem> {
        return listOf(
            SimpleItem(id = 1, name = context.getString(R.string.weather_surf_calm)),
            SimpleItem(id = 2, name = context.getString(R.string.weather_surf_light)),
            SimpleItem(id = 2, name = context.getString(R.string.weather_surf_moderate)),
            SimpleItem(id = 2, name = context.getString(R.string.weather_surf_rough)),
            SimpleItem(id = 2, name = context.getString(R.string.weather_surf_swell))
        )
    }
}