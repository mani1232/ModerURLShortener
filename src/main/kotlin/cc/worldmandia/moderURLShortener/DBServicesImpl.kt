package cc.worldmandia.moderURLShortener

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepo: UserRepo, val urlRepo: URLRepo, private val userAndURLEntity: UserAndURLRepo) :
    UserService {
    fun findByUserName(username: String): UserDTO? {
        return UserMapper.toDto(runBlocking { userRepo.findUserEntityByUsername(username).firstOrNull() }, null)
    }

    override suspend fun save(user: UserDTO): UserDTO? {
        val userEntity = userRepo.save(UserMapper.fromDto(user))
        val urls = urlRepo.saveAll(user.urls?.map { URLMapper.fromDto(it) }?.toMutableSet() ?: mutableSetOf())
        urls.collect {
            userAndURLEntity.save(UserAndURLEntity(userId = userEntity.id, urlId = it.id))
        }
        return UserMapper.toDto(userEntity, urls.mapNotNull { URLMapper.toDto(it, null) }.toSet().toMutableSet())
    }

    override suspend fun findById(id: Long): UserDTO? {
        return UserMapper.toDto(
            userRepo.findById(id),
            userAndURLEntity.findAllByUserId(id).mapNotNull { URLMapper.toDto(urlRepo.findById(it.urlId)!!, null) }
                .toSet()
                .toMutableSet()
        )
    }

    override suspend fun deleteById(id: Long) {
        userAndURLEntity.deleteAllByUserId(id)
        userRepo.deleteById(id)
    }

    override fun findAll(): Flow<UserDTO> {
        return userRepo.findAll().mapNotNull {
            UserMapper.toDto(
                it,
                userAndURLEntity.findAllByUserId(it.id)
                    .mapNotNull { url -> URLMapper.toDto(urlRepo.findById(url.urlId)!!, null) }.toSet().toMutableSet()
            )
        }
    }
}

@Service
class URLServiceImpl(val userRepo: UserRepo, val urlRepo: URLRepo, private val userAndURLEntity: UserAndURLRepo) :
    URLService {

    override suspend fun save(url: UrlDTO): UrlDTO? {
        val urlEntity = urlRepo.save(URLMapper.fromDto(url))
        val users = userRepo.saveAll(url.users?.map { UserMapper.fromDto(it) }?.toMutableSet() ?: mutableSetOf())
        users.collect {
            userAndURLEntity.save(UserAndURLEntity(urlId = urlEntity.id, userId = it.id))
        }
        return URLMapper.toDto(urlEntity, users.mapNotNull { UserMapper.toDto(it, null) }.toSet().toMutableSet())
    }

    override suspend fun findById(id: Long): UrlDTO? {
        return URLMapper.toDto(
            urlRepo.findById(id),
            userAndURLEntity.findAllByUrlId(id).mapNotNull { UserMapper.toDto(userRepo.findById(it.urlId)!!, null) }
                .toSet()
                .toMutableSet()
        )
    }

    override suspend fun deleteById(id: Long) {
        userAndURLEntity.deleteAllByUrlId(id)
        urlRepo.deleteById(id)
    }

    override fun findAll(): Flow<UrlDTO> {
        return urlRepo.findAll().mapNotNull {
            URLMapper.toDto(
                it,
                userAndURLEntity.findAllByUrlId(it.id)
                    .mapNotNull { user -> UserMapper.toDto(userRepo.findById(user.urlId)!!, null) }.toSet()
                    .toMutableSet()
            )
        }
    }
}