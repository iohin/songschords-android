package ru.iohin.songschords.core_api.navigation

import kotlin.reflect.KClass

interface AppNavigation {
    fun <T : Any> getNavigation(kclass: KClass<T>): T?
}