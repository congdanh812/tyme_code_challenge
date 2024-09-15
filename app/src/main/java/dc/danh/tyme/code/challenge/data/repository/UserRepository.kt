package dc.danh.tyme.code.challenge.data.repository

import dc.danh.tyme.code.challenge.data.api.GitHubApiService
import dc.danh.tyme.code.challenge.data.local.UserDao
import dc.danh.tyme.code.challenge.data.local.UserEntity
import dc.danh.tyme.code.challenge.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: GitHubApiService,
    private val userDao: UserDao
) : BaseRepository(), IUserRepository {

    override suspend fun getUsers(perPage: Int, since: Int): Flow<List<UserEntity>> = flow {
        // Emit cached data first
        val cachedUsers = userDao.getAllUsers().firstOrNull() ?: emptyList()
        emit(cachedUsers)

        // Fetch data from the network
        when (val result = safeApiCall { apiService.getUsers(perPage, since) }) {
            is Result.Success -> {
                val newUsers = result.data.map { user ->
                    UserEntity(
                        login = user.login,
                        avatarUrl = user.avatarUrl,
                        htmlUrl = user.htmlUrl,
                        location = user.location,
                        followers = 0, // Followers will be updated later from getUserDetail
                        following = 0 // Following will be updated later from getUserDetail
                    )
                }
                // Append new users to the cached data
                val updatedUsers = cachedUsers.toMutableList().apply {
                    addAll(newUsers)
                }
                userDao.insertUsers(updatedUsers) // Save updated list to cache
                emit(updatedUsers) // Emit the updated list
            }
            is Result.Error -> {
                // Handle error
            }
        }
    }

    override suspend fun getUserDetail(login: String): UserEntity? {
        // Check if the user is in the cache
        var cachedUser = userDao.getUserByLogin(login)

        // Fetch data from network for detailed info
        when (val result = safeApiCall { apiService.getUserDetail(login) }) {
            is Result.Success -> {
                val userDetail = result.data

                // Update cached user with detailed information if it's already cached
                cachedUser = cachedUser?.copy(
                    location = userDetail.location ?: cachedUser.location,
                    followers = userDetail.followers,
                    following = userDetail.following
                ) ?: UserEntity(
                    login = userDetail.login,
                    avatarUrl = userDetail.avatarUrl,
                    htmlUrl = userDetail.htmlUrl,
                    location = userDetail.location,
                    followers = userDetail.followers,
                    following = userDetail.following
                )

                // Cache the updated user details
                userDao.insertUser(cachedUser)
                return cachedUser
            }
            is Result.Error -> {
                // Handle error
                return cachedUser // Return cached data if network fails
            }
        }
    }
}
