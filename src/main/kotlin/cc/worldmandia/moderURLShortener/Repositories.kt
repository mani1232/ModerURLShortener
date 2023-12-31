package cc.worldmandia.moderURLShortener

import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.flow.Flow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Repository
interface UserRepo : CoroutineSortingRepository<UserEntity, Long>, CoroutineCrudRepository<UserEntity, Long>

@Repository
interface URLRepo : CoroutineSortingRepository<URLEntity, Long>, CoroutineCrudRepository<URLEntity, Long>

@Repository
interface UserAndURLRepo : CoroutineSortingRepository<UserAndURLEntity, Long>,
    CoroutineCrudRepository<UserAndURLEntity, Long> {
    fun findAllByUserId(userId: Long): Flow<UserAndURLEntity>
    fun findAllByUrlId(urlId: Long): Flow<UserAndURLEntity>

    suspend fun deleteAllByUrlId(urlId: Long)
    suspend fun deleteAllByUserId(userId: Long)

}

@Configuration
@EnableTransactionManagement
class DatabaseConfiguration {

    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory): ReactiveTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }
}