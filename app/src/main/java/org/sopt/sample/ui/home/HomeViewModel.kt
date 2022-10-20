package org.sopt.sample.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.datasource.local.FollowerDataSource
import com.example.data.entity.Follower
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import org.sopt.sample.di.DefaultDispatcher

@HiltViewModel
class HomeViewModel @Inject constructor(
    followerDataSource: FollowerDataSource,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    val followers: StateFlow<List<Follower>> = followerDataSource.getFollowers()
        .flowOn(defaultDispatcher)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )
}
