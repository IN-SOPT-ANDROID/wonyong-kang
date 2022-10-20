package com.example.compose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.di.DefaultDispatcher
import com.example.data.datasource.local.FollowerDataSource
import com.example.data.entity.Follower
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val followerDataSource: FollowerDataSource,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ContainerHost<HomeUiState, HomeSideEffect>, ViewModel() {
    override val container: Container<HomeUiState, HomeSideEffect> = container(HomeUiState())

    init {
        getFollowers()
    }

    private fun getFollowers() {
        viewModelScope.launch(defaultDispatcher) {
            followerDataSource.getFollowers().collect { followers ->
                intent {
                    reduce {
                        state.copy(followrs = followers)
                    }
                }
            }
        }
    }

    fun dispatchSideEffect(index: Int) = intent {
        postSideEffect(HomeSideEffect.SnackBarMessage("click index : $index"))
    }
}

data class HomeUiState(
    val followrs: List<Follower> = emptyList()
)

sealed class HomeSideEffect {
    data class SnackBarMessage(val text: String) : HomeSideEffect()
}
