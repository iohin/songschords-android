package ru.iohin.songschords.core.api

import android.app.Application

fun Application.requireCoreProvider() = (this as CoreProviderHolder).coreProvider