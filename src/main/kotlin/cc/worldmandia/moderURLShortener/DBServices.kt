package cc.worldmandia.moderURLShortener

import kotlinx.coroutines.flow.Flow

interface UserService {
    suspend fun save(user: UserDTO): UserDTO?

    suspend fun findById(id: Long): UserDTO?

    suspend fun deleteById(id: Long)

    fun findAll(): Flow<UserDTO>
}

interface URLService {
    suspend fun save(url: UrlDTO): UrlDTO?

    suspend fun findById(id: Long): UrlDTO?

    suspend fun deleteById(id: Long)

    fun findAll(): Flow<UrlDTO>
}