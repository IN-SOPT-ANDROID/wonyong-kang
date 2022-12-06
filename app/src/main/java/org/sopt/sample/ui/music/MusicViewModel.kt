package org.sopt.sample.ui.music

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entity.Music
import com.example.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<List<Music>> = MutableStateFlow(emptyList())
    val uiState = _uiState.asStateFlow()

    init {
        getMusics()
    }

    fun getMusics() {
        viewModelScope.launch {
            musicRepository.getMusics()
                .onSuccess { musics ->
                    _uiState.value = musics
                }
                .onFailure { Log.e("MusicViewModel", "error : ${it.message}") }
        }
    }
}
