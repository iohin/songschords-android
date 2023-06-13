package ru.iohin.songschords.core.data

import ru.iohin.songschords.core.api.entity.ArtistFull
import ru.iohin.songschords.core.api.entity.ArtistShort
import ru.iohin.songschords.core.api.entity.Resource
import ru.iohin.songschords.core.api.entity.SongFull
import ru.iohin.songschords.core.api.entity.SongShort
import ru.iohin.songschords.core.rest.RestArtistFull
import ru.iohin.songschords.core.rest.RestArtistShort
import ru.iohin.songschords.core.rest.RestResource
import ru.iohin.songschords.core.rest.RestSongFull
import ru.iohin.songschords.core.rest.RestSongShort

fun <T, O> RestResource<T>.toResource(dataConvert: (List<T>) -> O) = Resource(
    totalCount = meta.totalCount,
    limit = meta.limit,
    offset = meta.offset,
    data = dataConvert(objects)
)

fun RestArtistShort.toArtistShort() = ArtistShort(
    id = id,
    name = name,
    imageUrl = imageUrl
)

fun List<RestArtistShort>.toArtistShortList() = map { it.toArtistShort() }

fun RestArtistFull.toArtistFull() = ArtistFull(
    id,
    name,
    nameAliases.split(","),
    description,
    imageUrl
)

fun RestSongShort.toSongShort() = SongShort(
    id,
    name,
    artist,
    artistName
)

fun List<RestSongShort>.toSongShortList() = map { it.toSongShort() }

fun RestSongFull.toSongFull() = SongFull(
    id,
    name,
    artist,
    artistName,
    author,
    content,
    copyright
)
