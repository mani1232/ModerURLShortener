package cc.worldmandia.moderURLShortener

object UserMapper {
    fun toDto(userEntity: UserEntity?, urls: MutableSet<UrlDTO>?): UserDTO? {
        return if (userEntity != null) UserDTO(
            id = userEntity.id,
            displayName = userEntity.displayName,
            password = userEntity.password,
            token = userEntity.token,
            username = userEntity.username,
            urlIds = urls
        ) else null
    }

    fun fromDto(userEntity: UserDTO): UserEntity {
        return UserEntity(
            id = userEntity.id,
            displayName = userEntity.displayName,
            password = userEntity.password,
            token = userEntity.token,
            username = userEntity.username,
        )
    }
}

object URLMapper {
    fun toDto(urlEntity: URLEntity?, users: MutableSet<UserDTO>?): UrlDTO? {
        return if (urlEntity != null) UrlDTO(
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
        ) else null
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