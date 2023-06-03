package ru.iohin.songschords.core_api

import android.app.Application

fun Application.requireCoreProvider() = (this as CoreProviderHolder).coreProvider