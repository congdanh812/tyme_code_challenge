package dc.danh.tyme.code.challenge.ui.detail

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
class DetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userDetail = MutableLiveData<UserEntity?>()
    val userDetail: LiveData<UserEntity?> = _userDetail

    fun getUserDetail(login: String) {
        viewModelScope.launch {
            val user = userRepository.getUserDetail(login)
            _userDetail.postValue(user)
        }
    }
}
