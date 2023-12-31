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

@RestController
@RequestMapping("/v1/api/url")
class URLApiRESTController {

    @Autowired
    lateinit var urlRepository: URLServiceImpl

    @PostMapping("/")
    suspend fun create(@RequestBody newURL: UrlDTO) =
        if (urlRepository.findById(newURL.id) != null) urlRepository.save(newURL) else throw AlreadyExistException(
            newURL.id
        )

    @GetMapping("/{id}")
    suspend fun findOne(@PathVariable id: Long) = urlRepository.findById(id) ?: throw NotFoundException(id)

    @GetMapping("/")
    fun findAll() = urlRepository.findAll()

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: Long, @RequestBody updateURL: UrlDTO) =
        if (urlRepository.findById(id) != null) urlRepository.save(updateURL) else throw NotFoundException(id)

    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable id: Long) =
        if (urlRepository.findById(id) != null) urlRepository.deleteById(id) else throw NotFoundException(id)

}