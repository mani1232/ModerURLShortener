package cc.worldmandia.moderURLShortener

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api")
class ApiRESTController {

    @Autowired
    lateinit var userRepository: UserRepo

    @GetMapping("/init")
    suspend fun init(): UserEntity {
        return userRepository.save(UserEntity(username = "hjkyhjk", displayName = "sdfsdg", password = "dfgdfg", token = "dfgdfgdfh"))
    }

    @GetMapping("/{id}")
    suspend fun findOne(@PathVariable id: Long): UserEntity? {
        return userRepository.findById(id)
    }

    @GetMapping("/users")
    fun findAll(): Flow<UserEntity> {
        return userRepository.findAll()
    }

    @GetMapping("/test")
    fun test(): Flow<String> {
        return flow {
            emit("erfhdfrghfdghfg")
        }
    }

}