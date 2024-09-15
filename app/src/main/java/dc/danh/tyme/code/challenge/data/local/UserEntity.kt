package dc.danh.tyme.code.challenge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a user entity in the database.
 *
 * @property login The user's login (primary key).
 * @property avatarUrl The URL of the user's avatar image.
 * @property htmlUrl The URL of the user's profile page.
 * @property location The user's location (nullable).
 * @property followers The number of followers the user has.
 * @property following The number of users the user is following.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val location: String?,
    val followers: Int,
    val following: Int
)
