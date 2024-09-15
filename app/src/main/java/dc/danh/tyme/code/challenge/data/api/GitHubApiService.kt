package dc.danh.tyme.code.challenge.data.api

import dc.danh.tyme.code.challenge.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit interface for GitHub API
interface GitHubApiService {

    @GET("users")
    // Fetch a list of users with a specified page size and starting point
    suspend fun getUsers(@Query("per_page") perPage: Int, @Query("since") since: Int): List<User>

    @GET("users/{login}")
    // Fetch user details based on their login
    suspend fun getUserDetail(@Path("login") login: String): User
}
