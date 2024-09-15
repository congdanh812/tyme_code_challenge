package dc.danh.tyme.code.challenge.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dc.danh.tyme.code.challenge.data.local.UserEntity
import dc.danh.tyme.code.challenge.data.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> = _users

    private var currentSince = 0
    private val perPage = 20

    private var isLoading = false

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            userRepository.getUsers(perPage, currentSince).collect { userList ->
                _users.postValue(userList)
                isLoading = false
            }
        }
    }

    fun fetchMoreUsers() {
        if (isLoading) return
        currentSince += perPage
        fetchUsers()
    }
}
