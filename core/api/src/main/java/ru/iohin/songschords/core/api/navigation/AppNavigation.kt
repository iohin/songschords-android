package ru.iohin.songschords.core.api.navigation

import kotlin.reflect.KClass

interface AppNavigation {
    fun <T : Any> getNavigation(kclass: KClass<T>): T?
}