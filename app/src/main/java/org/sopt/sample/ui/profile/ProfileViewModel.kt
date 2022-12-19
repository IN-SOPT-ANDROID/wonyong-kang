package org.sopt.sample.ui.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    private val _musicImg: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val musicImg = _musicImg.asStateFlow()

    fun setUri(img: Uri) {
        _musicImg.value = img
    }

    fun postMusic(image: MultipartBody.Part) {
        viewModelScope.launch {
            musicRepository.postMusic(
                title = "Hous",
                singer = "KWY",
                image = image
            )
                .onSuccess { Log.d("MUSIC", "SUCCES") }
                .onFailure { Log.d("MUSIC", "FAIL : ${it.message}") }
        }
    }
}
