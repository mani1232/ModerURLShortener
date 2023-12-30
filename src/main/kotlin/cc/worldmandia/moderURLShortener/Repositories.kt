package cc.worldmandia.moderURLShortener

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo: CoroutineCrudRepository<UserEntity, Long>

@Repository
interface URLRepo: CoroutineCrudRepository<URLEntity, Long>