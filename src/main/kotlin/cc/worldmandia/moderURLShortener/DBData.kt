package cc.worldmandia.moderURLShortener

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("users")
data class UserEntity(
        @Id
        var id: Long = 0,
        @Column("display_name")
        val displayName: String,
        @Column("username")
        val username: String,
        @Column("password")
        val password: String,
        @Column("token")
        val token: String,
        @MappedCollection
        @Column("urls")
        val urls: List<Long> = listOf(),
)

@Table("urls")
data class URLEntity(
        @Id
        val id: Long = 0,
        val title: String,
        val description: String,
        @Column("created_date")
        val createdDate: LocalDate,
        @Column("end_at")
        val endAt: LocalDate,
        val enabled: Boolean,
        @Column("click_count")
        val clickCount: Int,
        @Column("short_url")
        val shortUrl: String,
        @Column("full_url")
        val fullUrl: String,
        @Column("user_id")
        val userId: Long
)