package com.example.compose.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.compose.R
import com.example.compose.navigation.MainContentNavGraph
import com.example.data.entity.Follower
import com.ramcosta.composedestinations.annotation.Destination
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@MainContentNavGraph(start = true)
@Destination("home")
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.collectAsState().value
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    homeViewModel.collectSideEffect { homeSideEffect ->
        handleSideEffect(scaffoldState, homeSideEffect)
    }
    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding),
                    text = stringResource(R.string.home_title),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
            }
            itemsIndexed(state.followrs) { index, follower ->
                FollowerItem(
                    follower = follower,
                    index = index,
                    onClick = homeViewModel::dispatchSideEffect
                )
            }
        }
    }
}

@Composable
private fun FollowerItem(follower: Follower, index: Int, onClick: (Int) -> Unit) {
    Surface(
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick(index) }
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = follower.avatar,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop,
                fallback = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            Text(
                text = follower.name,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

private suspend fun handleSideEffect(scaffoldState: ScaffoldState, sideEffect: HomeSideEffect) {
    when (sideEffect) {
        is HomeSideEffect.SnackBarMessage -> showSnackBar(scaffoldState, sideEffect.text)
    }
}

private suspend fun showSnackBar(scaffoldState: ScaffoldState, text: String) {
    scaffoldState.snackbarHostState.showSnackbar(message = text)
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
