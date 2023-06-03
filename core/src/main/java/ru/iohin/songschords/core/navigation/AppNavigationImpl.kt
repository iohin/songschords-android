package ru.iohin.songschords.core.navigation

import ru.iohin.songschords.core.api.navigation.AppNavigation
import javax.inject.Inject
import kotlin.reflect.KClass

class AppNavigationImpl @Inject constructor(private val navigations: Map<KClass<out Any>, Any>):
    AppNavigation {

    override fun <T : Any> getNavigation(kclass: KClass<T>) =
        navigations[kclass] as? T
}