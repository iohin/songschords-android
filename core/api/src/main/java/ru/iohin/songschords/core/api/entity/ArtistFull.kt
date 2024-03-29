package ru.iohin.songschords.core.api.entity

data class ArtistFull(
    val id: Int,
    val name: String,
    val nameAliases: List<String>,
    val description: String,
    val imageUrl: String?
) {
    fun short() = ArtistShort(id, name, imageUrl)
}
