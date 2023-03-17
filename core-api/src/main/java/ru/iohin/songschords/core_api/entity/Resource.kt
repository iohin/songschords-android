package ru.iohin.songschords.core_api.entity

data class Resource<T>(
    val totalCount: Int,
    val limit: Int,
    val offset: Int,
    val data: T
)
