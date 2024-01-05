package cc.worldmandia.moderURLShortener

import org.springframework.data.annotation.Transient
import java.time.LocalDate

data class UserDTO(
    val id: Long = 0,
    var displayName: String,
    val username: String,
    var password: String,
    var token: String,
    @Transient
    val urls: MutableSet<UrlDTO>? = mutableSetOf(),
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
    @Transient
    val users: MutableSet<UserDTO>? = mutableSetOf()
)