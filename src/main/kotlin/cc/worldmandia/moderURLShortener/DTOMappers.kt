package cc.worldmandia.moderURLShortener

class UserMapper {
    companion object {
        fun toDto(userEntity: UserEntity, urls: MutableSet<UrlDTO>?): UserDTO {
            return UserDTO(id = userEntity.id,
                displayName = userEntity.displayName,
                password = userEntity.password,
                token = userEntity.token,
                username = userEntity.username,
                urlIds = urls
            )
        }
        fun fromDto(userEntity: UserDTO): UserEntity {
            return UserEntity(id = userEntity.id,
                displayName = userEntity.displayName,
                password = userEntity.password,
                token = userEntity.token,
                username = userEntity.username,
            )
        }
    }
}

class URLMapper {
    companion object {
        fun toDto(urlEntity: URLEntity, users: MutableSet<UserDTO>?): UrlDTO {
            return UrlDTO(
                id = urlEntity.id,
                clickCount = urlEntity.clickCount,
                createdDate = urlEntity.createdDate,
                enabled = urlEntity.enabled,
                description = urlEntity.description,
                endAt = urlEntity.endAt,
                fullUrl = urlEntity.fullUrl,
                shortUrl = urlEntity.shortUrl,
                title = urlEntity.title,
                userIds = users,
            )
        }
        fun fromDto(urlEntity: UrlDTO): URLEntity {
            return URLEntity(
                id = urlEntity.id,
                clickCount = urlEntity.clickCount,
                createdDate = urlEntity.createdDate,
                enabled = urlEntity.enabled,
                description = urlEntity.description,
                endAt = urlEntity.endAt,
                fullUrl = urlEntity.fullUrl,
                shortUrl = urlEntity.shortUrl,
                title = urlEntity.title,
            )
        }
    }
}