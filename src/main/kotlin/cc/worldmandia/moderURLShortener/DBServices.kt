package cc.worldmandia.moderURLShortener

interface UserService {
    suspend fun save(user: UserDTO): UserDTO?

    suspend fun findById(id: Long): UserDTO?

    suspend fun deleteById(id: Long)
}

interface URLService {
    suspend fun save(url: UrlDTO): UrlDTO?

    suspend fun findById(id: Long): UrlDTO?

    suspend fun deleteById(id: Long)
}