package cc.worldmandia.moderURLShortener

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/api/user")
class UserApiRESTController {

    @Autowired
    lateinit var userRepository: UserServiceImpl

    @PostMapping("/")
    suspend fun create(@RequestBody newUser: UserDTO) =
        if (userRepository.findById(newUser.id) != null) userRepository.save(newUser) else throw AlreadyExistException(
            newUser.id
        )

    @GetMapping("/{id}")
    suspend fun findOne(@PathVariable id: Long) = userRepository.findById(id) ?: throw NotFoundException(id)

    @GetMapping("/")
    fun findAll() = userRepository.findAll()

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: Long, @RequestBody updateUser: UserDTO) =
        if (userRepository.findById(id) != null) userRepository.save(updateUser) else throw NotFoundException(id)

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: Long) =
        if (userRepository.findById(id) != null) userRepository.deleteById(id) else throw NotFoundException(id)

}