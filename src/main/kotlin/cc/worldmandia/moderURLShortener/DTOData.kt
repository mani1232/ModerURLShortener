package cc.worldmandia.moderURLShortener

import java.time.LocalDate

data class UserDTO(
    val id: Long = 0,
    var displayName: String,
    val username: String,
    var password: String,
    var token: String,
    val urlIds: MutableSet<UrlDTO>? = mutableSetOf(),
)

data class UrlDTO(
    val id: Long = 0,
    var title: String,
    var description: String,
    var createdDate: LocalDate = LocalDate.now(),
    var endAt: LocalDate,
    var enabled: Boolean,
    var clickCount: Int,
    var shortUrl: String,
    var fullUrl: String,
    val userIds: MutableSet<UserDTO>? = mutableSetOf()
)