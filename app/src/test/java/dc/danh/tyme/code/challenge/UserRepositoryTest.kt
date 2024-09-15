package dc.danh.tyme.code.challenge

import dc.danh.tyme.code.challenge.data.api.GitHubApiService
import dc.danh.tyme.code.challenge.data.local.UserDao
import dc.danh.tyme.code.challenge.data.local.UserEntity
import dc.danh.tyme.code.challenge.data.model.User
import dc.danh.tyme.code.challenge.data.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class UserRepositoryTest {

    private lateinit var userRepository: UserRepository
    private val mockApiService = mock<GitHubApiService>()
    private val mockUserDao = mock<UserDao>()

    @Before
    fun setup() {
        userRepository = UserRepository(mockApiService, mockUserDao)
    }

    @Test
    fun getUsers_fetchesCachedData() = runTest {
        // Arrange
        val cachedUsers = listOf(
            UserEntity("user1", "url1", "html1", "Location1", 1, 2),
            UserEntity("user2", "url2", "html2", "Location2", 3, 4)
        )
        `when`(mockUserDao.getAllUsers()).thenReturn(flow { emit(cachedUsers) })

        // Act
        val result = userRepository.getUsers(20, 0).firstOrNull()

        // Assert
        assertEquals(cachedUsers, result)
        verify(mockUserDao).getAllUsers()
    }

    @Test
    fun getUserDetail_returnsCachedUserWhenNetworkFails() = runTest {
        // Arrange
        val cachedUser = UserEntity(
            login = "testuser",
            avatarUrl = "url",
            htmlUrl = "html",
            location = "HCM",
            followers = 10,
            following = 5
        )
        `when`(mockUserDao.getUserByLogin("testuser")).thenReturn(cachedUser)

        // Mock the API service to return a valid user detail
        val apiUserDetail = User(
            login = "testuser",
            avatarUrl = "url",
            htmlUrl = "html",
            location = "HCM",
            followers = 10,
            following = 5
        )
        `when`(mockApiService.getUserDetail("testuser")).thenReturn((apiUserDetail))

        // Act
        val result = userRepository.getUserDetail("testuser")

        // Assert
        assertEquals(cachedUser.login, result?.login)
        verify(mockUserDao).getUserByLogin("testuser")
        verify(mockUserDao).insertUser(cachedUser)
    }
}
