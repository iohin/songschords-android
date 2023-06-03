package ru.iohin.songschords.core_api

import android.app.Activity
import ru.iohin.songschords.core_api.di.ActivityProvider

interface ActivityProviderHolder {
    val activityProvider: ActivityProvider
}

fun Activity.requireActivityProvider() = (this as ActivityProviderHolder).activityProvider