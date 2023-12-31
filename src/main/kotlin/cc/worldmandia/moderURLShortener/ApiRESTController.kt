package cc.worldmandia.moderURLShortener

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/v1/api")
class ApiRESTController {

    @Autowired
    lateinit var userRepository: UserServiceImpl

    @GetMapping("/init")
    suspend fun init(): UserDTO? {
        return userRepository.save(UserDTO(username = "hjkyhjk1", displayName = "sdfsdg", password = "dfgdfg", token = "dfgdfgdfh1", urlIds = mutableSetOf(
            UrlDTO(title = "fghjgh1", description = "fghfgh", clickCount = 10, enabled = true, endAt = LocalDate.now(), fullUrl = "dfgdfg", shortUrl = "dfgdfgdf"),
            UrlDTO(title = "fghjgh2", description = "fghfgh", clickCount = 101000, enabled = false, endAt = LocalDate.now(), fullUrl = "dfgdfg", shortUrl = "dfgdfgdf"),
            UrlDTO(title = "fghjgh3", description = "fghfgh", clickCount = 1010, enabled = true, endAt = LocalDate.now(), fullUrl = "dfgdfg", shortUrl = "dfgdfgdf"),
        )))
    }

    @GetMapping("/update")
    suspend fun update(): UserDTO? {
        return userRepository.save(UserDTO(id = 1, username = "hjkyhjk1updated", displayName = "sdfsdgdsgfdfsg", password = "dfgdfggdfgdfgfg", token = "dfgdfgdfh1gdfgdfgdfg"))
    }

    @GetMapping("/{id}")
    suspend fun findOne(@PathVariable id: Long): UserDTO? {
        return userRepository.findById(id)
    }

    //@GetMapping("/users")
    //fun findAll(): Flow<UserEntity> {
    //    return userRepository.findById(0)
    //}

    @GetMapping("/test")
    fun test(): Flow<String> {
        return flow {
            emit("erfhdfrghfdghfg")
        }
    }

}