package com.example.uthsmarttasks.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.uthsmarttasks.model.Task



interface TaskApiService {
    @GET("tasks")
    suspend fun getTasks(): List<Task>
}

class TaskViewModel : ViewModel() {
    private val api = Retrofit.Builder()
        .baseUrl("https://amock.io/api/researchUTH/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TaskApiService::class.java)

    var tasks by mutableStateOf<List<Task>>(emptyList())
        private set

    var isError by mutableStateOf(false)
        private set

    fun fetchTasks() {
        viewModelScope.launch {
            try {
                tasks = api.getTasks()
                isError = false
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi tải danh sách tasks", e)
                isError = true
            }
        }
    }
}

@Composable
fun ListScreen(navController: NavController, viewModel: TaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchTasks()
    }

    if (viewModel.isError) {
        navController.navigate("list_error")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            items(viewModel.tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("detail_list/${task.id}") },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
