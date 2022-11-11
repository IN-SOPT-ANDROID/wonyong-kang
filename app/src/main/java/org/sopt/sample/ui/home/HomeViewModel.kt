package org.sopt.sample.ui.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.Follower
import com.example.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _followers: MutableStateFlow<List<Follower>> = MutableStateFlow(emptyList())
    val followers: StateFlow<List<Follower>> = _followers.asStateFlow()

    init {
        viewModelScope.launch {
            mainRepository.getFollowers(1)
                .onSuccess { result ->
                    _followers.value = result
                }
                .onFailure {
                    Log.e(TAG, "HomeViewModel : ${it.message}")
                }
        }
    }
}
