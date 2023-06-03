package ru.iohin.songschords.core.api.di

import ru.iohin.songschords.core.api.navigation.AppNavigation

interface NavigationProvider {
    fun getAppNavigation(): AppNavigation
}