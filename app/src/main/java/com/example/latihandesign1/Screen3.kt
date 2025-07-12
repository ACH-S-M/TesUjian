package com.example.latihandesign1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.latihandesign1.ui.theme.Latihandesign1Theme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class Screen3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Latihandesign1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen3Prev()
                }
            }
        }
    }
}

@Preview
@Composable
fun Screen3Prev() {
    Latihandesign1Theme {
        UserList(viewModel = UserViewModel(), onBack = {})
    }
}

@Composable
fun UserList(viewModel: UserViewModel, onBack: () -> Unit) {
    val isRefresh = viewModel.isRefresh
    val users = viewModel.user
    val isLoading = viewModel.isLoading
    val listState = rememberLazyListState()
    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isRefresh),
        onRefresh = {
            viewModel.getUsers(refresh = true) })
    {
        LazyColumn(state = listState, modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)) {
            items(users){ user -> UserItem(user = user) {
                viewModel.selectuser(user)
                onBack()
               }
            }
            if (isLoading && !isRefresh) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (users.isEmpty() && !isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No users found", color = Color.Black)
                    }
                }
            }
        }
    }
}

