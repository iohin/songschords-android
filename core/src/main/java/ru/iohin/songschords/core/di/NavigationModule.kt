package ru.iohin.songschords.core.di

import dagger.Module
import dagger.Provides
import ru.iohin.songschords.core.api.di.ActivityScope
import ru.iohin.songschords.core.navigation.AppNavigationImpl
import ru.iohin.songschords.core.api.navigation.AppNavigation
import kotlin.reflect.KClass

@Module
class NavigationModule(private val navigations: Map<KClass<out Any>, Any>) {
    @Provides
    @ActivityScope
    fun providesAppNavigation(): AppNavigation =
        AppNavigationImpl(navigations)
}