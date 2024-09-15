package dc.danh.tyme.code.challenge.data.model

import com.google.gson.annotations.SerializedName


/**
 * Data class representing a user from the GitHub API.
 *
 * @property login The username of the user.
 * @property avatarUrl The URL of the user's avatar image.
 * @property htmlUrl The URL of the user's GitHub profile.
 * @property location The user's location (can be null).
 * @property followers The number of followers the user has.
 * @property following The number of other users the user is following.
 */
data class User(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("location") val location: String?,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int
)
