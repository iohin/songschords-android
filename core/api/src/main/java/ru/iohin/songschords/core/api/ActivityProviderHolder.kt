package ru.iohin.songschords.core.api

import android.app.Activity
import ru.iohin.songschords.core.api.di.ActivityProvider

interface ActivityProviderHolder {
    val activityProvider: ActivityProvider
}

fun Activity.requireActivityProvider() = (this as ActivityProviderHolder).activityProvider