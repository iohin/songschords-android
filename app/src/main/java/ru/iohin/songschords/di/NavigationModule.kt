package ru.iohin.songschords.di

import androidx.navigation.NavController
import dagger.Module
import dagger.Provides
import ru.iohin.songschords.core_api.navigation.AppNavigation
import ru.iohin.songschords.navigation.AppNavigationImpl
import ru.iohin.songschords.navigation.NavControllerHolder

@Module
class NavigationModule(private val navControllerHolder: NavControllerHolder) {
    @Provides
    fun providesAppNavigation(): AppNavigation =
        AppNavigationImpl(navControllerHolder.navController)
}