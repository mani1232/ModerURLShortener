package cc.worldmandia.moderURLShortener

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepo: UserRepo, val urlRepo: URLRepo, private val userAndURLEntity: UserAndURLRepo) :
    UserService {

    override suspend fun save(user: UserDTO): UserDTO? {
        val userEntity = userRepo.save(UserMapper.fromDto(user))
        val urls = urlRepo.saveAll(user.urlIds?.map { URLMapper.fromDto(it) }?.toMutableSet() ?: mutableSetOf())
        urls.collect {
            userAndURLEntity.save(UserAndURLEntity(userId = userEntity.id, urlId = it.id))
        }
        return UserMapper.toDto(userEntity, urls.map { URLMapper.toDto(it, null) }.toSet().toMutableSet())
    }

    override suspend fun findById(id: Long): UserDTO? {
        return UserMapper.toDto(
            userRepo.findById(id)!!,
            userAndURLEntity.findAllByUserId(id).map { URLMapper.toDto(urlRepo.findById(it.urlId)!!, null) }.toSet()
                .toMutableSet()
        )
    }

    override suspend fun deleteById(id: Long) {
        userAndURLEntity.deleteAllByUserId(id)
        userRepo.deleteById(id)
    }

    override fun findAll(): Flow<UserDTO> {
        return userRepo.findAll().map {
            UserMapper.toDto(
                it,
                userAndURLEntity.findAllByUserId(it.id)
                    .map { url -> URLMapper.toDto(urlRepo.findById(url.urlId)!!, null) }.toSet().toMutableSet()
            )
        }
    }
}

@Service
class URLServiceImpl(val userRepo: UserRepo, val urlRepo: URLRepo, private val userAndURLEntity: UserAndURLRepo) :
    URLService {

    override suspend fun save(url: UrlDTO): UrlDTO? {
        val urlEntity = urlRepo.save(URLMapper.fromDto(url))
        val users = userRepo.saveAll(url.userIds?.map { UserMapper.fromDto(it) }?.toMutableSet() ?: mutableSetOf())
        users.collect {
            userAndURLEntity.save(UserAndURLEntity(urlId = urlEntity.id, userId = it.id))
        }
        return URLMapper.toDto(urlEntity, users.map { UserMapper.toDto(it, null) }.toSet().toMutableSet())
    }

    override suspend fun findById(id: Long): UrlDTO? {
        return URLMapper.toDto(
            urlRepo.findById(id)!!,
            userAndURLEntity.findAllByUrlId(id).map { UserMapper.toDto(userRepo.findById(it.urlId)!!, null) }.toSet()
                .toMutableSet()
        )
    }

    override suspend fun deleteById(id: Long) {
        userAndURLEntity.deleteAllByUrlId(id)
        urlRepo.deleteById(id)
    }

    override fun findAll(): Flow<UrlDTO> {
        return urlRepo.findAll().map {
            URLMapper.toDto(
                it,
                userAndURLEntity.findAllByUrlId(it.id)
                    .map { user -> UserMapper.toDto(userRepo.findById(user.urlId)!!, null) }.toSet().toMutableSet()
            )
        }
    }
}