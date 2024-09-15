package dc.danh.tyme.code.challenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE login = :login")
    // Fetch a user by their login
    suspend fun getUserByLogin(login: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // Insert a user into the database
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users")
    // Fetch all users from the database
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    // Insert a list of users into the database
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    // Clear all users from the database
    suspend fun clearUsers()
}
