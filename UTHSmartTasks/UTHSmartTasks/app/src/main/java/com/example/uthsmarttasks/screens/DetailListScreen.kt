package com.example.uthsmarttasks.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.uthsmarttasks.data.Task



interface DetailApiService {
    @GET("tasks/{id}")
    suspend fun getTaskDetail(@Path("id") id: Int): Task
}

class DetailViewModel : ViewModel() {
    private val api = Retrofit.Builder()
        .baseUrl("https://amock.io/api/researchUTH/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DetailApiService::class.java)

    var taskDetail by mutableStateOf<Task?>(null)
        private set

    var isError by mutableStateOf(false)
        private set

    fun fetchTaskDetail(id: Int) {
        viewModelScope.launch {
            try {
                taskDetail = api.getTaskDetail(id)
                isError = false
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi tải chi tiết task", e)
                isError = true
            }
        }
    }
}

@Composable
fun DetailListScreen(navController: NavController, taskId: Int, viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    LaunchedEffect(taskId) {
        viewModel.fetchTaskDetail(taskId)
    }

    if (viewModel.isError) {
        navController.navigate("list_error")
    } else {
        viewModel.taskDetail?.let { task ->
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(text = task.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = task.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
