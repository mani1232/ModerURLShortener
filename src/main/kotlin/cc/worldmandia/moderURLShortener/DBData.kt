package cc.worldmandia.moderURLShortener

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("users")
data class UserEntity(
    @Id
    val id: Long = 0,
    @Column("display_name")
    var displayName: String,
    @Column("username")
    val username: String,
    @Column("password")
    var password: String,
    @Column("token")
    var token: String,
)

@Table("urls")
data class URLEntity(
    @Id
    val id: Long = 0,
    var title: String,
    var description: String,
    @Column("created_date")
    var createdDate: LocalDate,
    @Column("end_at")
    var endAt: LocalDate,
    var enabled: Boolean,
    @Column("click_count")
    var clickCount: Int,
    @Column("short_url")
    var shortUrl: String,
    @Column("full_url")
    var fullUrl: String
)

@Table("users_urls")
data class UserAndURLEntity(
    @Column("user_id")
    val userId: Long = 0,
    @Column("url_id")
    val urlId: Long = 0,
)