package ru.iohin.songschords.core.di

import dagger.Module
import dagger.Provides
import ru.iohin.songschords.core.navigation.AppNavigationImpl
import ru.iohin.songschords.core_api.navigation.AppNavigation
import kotlin.reflect.KClass

@Module
class NavigationModule(private val navigations: Map<KClass<out Any>, Any>) {
    @Provides
    fun providesAppNavigation(): AppNavigation =
        AppNavigationImpl(navigations)
}