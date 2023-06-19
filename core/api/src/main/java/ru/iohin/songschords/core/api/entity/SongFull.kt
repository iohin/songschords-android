package ru.iohin.songschords.core.api.entity

data class SongFull(
    val id: Int,
    val name: String,
    val artistId: Int,
    val artistName: String,
    val author: String,
    val content: String,
    val copyright: String,
) {
    fun getChords() =
        """\[crd]([^\[]+)\[/crd]"""
            .toRegex()
            .findAll(content)
            .map { it.groupValues[1] }
            .distinct()
            .toList()
}
