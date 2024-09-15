package dc.danh.tyme.code.challenge.domain.repository

import dc.danh.tyme.code.challenge.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing user data.
 */
interface IUserRepository {
    suspend fun getUsers(perPage: Int, since: Int): Flow<List<UserEntity>>
    suspend fun getUserDetail(login: String): UserEntity?
}
