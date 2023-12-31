package cc.worldmandia.moderURLShortener

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepo: UserRepo, private val urlRepo: URLRepo, private val userAndURLEntity: UserAndURLRepo) : UserService {

    override suspend fun save(user: UserDTO): UserDTO? {
        val userEntity = userRepo.save(UserMapper.fromDto(user))
        val urls = urlRepo.saveAll(user.urlIds?.map { URLMapper.fromDto(it) }?.toMutableSet() ?: mutableSetOf()).toSet()
        urls.forEach {
            userAndURLEntity.save(UserAndURLEntity(userId = userEntity.id, urlId = it.id))
        }
        return UserMapper.toDto(userEntity, urls.map { URLMapper.toDto(it, null) }.toMutableSet())
    }

    override suspend fun findById(id: Long): UserDTO? {
        return UserMapper.toDto(userRepo.findById(id)!!, userAndURLEntity.findAllByUserId(id).map { URLMapper.toDto(it, null) }.toSet().toMutableSet())
    }

    override suspend fun deleteById(id: Long) {
        userAndURLEntity.deleteAllByUserId(id)
        userRepo.deleteById(id)
    }
}