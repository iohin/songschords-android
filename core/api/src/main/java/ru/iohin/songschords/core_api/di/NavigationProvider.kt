package ru.iohin.songschords.core_api.di

import ru.iohin.songschords.core_api.navigation.AppNavigation

interface NavigationProvider {
    fun getAppNavigation(): AppNavigation
}