package com.example.uthsmarttasks.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uthsmarttasks.R
import com.example.uthsmarttasks.data.APIAnswer
import com.example.uthsmarttasks.data.Client
import com.example.uthsmarttasks.data.Task
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(onItemClick: (Task?) -> Unit, onEmptyList: () -> Unit) {
    var apiResponse by remember { mutableStateOf<APIAnswer?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = Client.apiServer.getTasks()
                apiResponse = response
                if(response.data.isEmpty()) {
                    onEmptyList()
                }
            } catch (e: Exception) {
                error = "Failed to load tasks: ${e.message}"
                apiResponse = null
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "UTHSmartTasks",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xff00ff00)
                        )
                        Text(
                            text = "Ứng dụng làm việc thông minh UTH",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xff00ff00)
                        )
                    }
                },
                navigationIcon = {
                    Box(modifier = Modifier
                        .background(Color(0xFFD7E8FF))
                        .clip(RoundedCornerShape(65.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_uth),
                            contentDescription = "UTH Logo",
                            modifier = Modifier
                                .size(100.dp)
                                .padding(16.dp)
                        )
                    }

                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Thông báo",
                            tint = Color(0xffffff00)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(5.dp),
                containerColor = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Trang chủ"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Lịch"
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = "Tài liệu"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Cài đặt"
                        )
                    }
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier
                    .size(56.dp)
                    .offset(y = (50).dp),
                containerColor = Color(0xff0000ff),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Thêm"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            when {
                error != null -> {
                    Text(text = error ?: "Unknown error", color = MaterialTheme.colorScheme.error)
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response = Client.apiServer.getTasks()
                                    apiResponse = response
                                    error = null
                                } catch (e: Exception) {
                                    error = "Failed to load tasks: ${e.message}"
                                    apiResponse = null
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Retry")
                    }
                }
                apiResponse == null -> {
                    Text(text = "Loading...")
                }
                apiResponse?.data?.isEmpty() == true -> {
                    onItemClick(null) // Chuyển sang List Empty
                }
                else -> {
                    LazyColumn {
                        items(apiResponse?.data ?: emptyList()) { task ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable { onItemClick(task) },
                                colors = CardDefaults.cardColors(
                                    containerColor = when (task.status) {
                                        "In Progress" -> Color(0xffff0000) // Màu hồng nhạt
                                        "Pending" -> Color(0xFF000080) // Màu xanh nhạt
                                        else -> Color.White
                                    }
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = task.status == "Hoàn thành",
                                        onCheckedChange = {},
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = task.title,
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Text(
                                            text = task.description,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = Color.Black
                                            )
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Status: ${task.status}",
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                color = Color.Black,
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = task.dueDate.substring(11, 16) + " " + task.dueDate.substring(0, 10),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    MaterialTheme {
        ListScreen(
            onItemClick = {},
            onEmptyList = {}
        )
    }
}