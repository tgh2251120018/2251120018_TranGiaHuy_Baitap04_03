package com.example.uthsmarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.uthsmarttasks.ui.screens.DetailListScreen
import com.example.uthsmarttasks.ui.screens.ListErrorScreen
import com.example.uthsmarttasks.ui.screens.ListScreen
import com.example.uthsmarttasks.ui.theme.UTHSmartTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTHSmartTasksTheme {
                val navController = rememberNavController()
                MainNavigation(navController)
            }
        }
    }
}

@Composable
fun MainNavigation(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("list") {
                ListScreen(
                    onItemClick = { task ->
                        task?.let { navController.navigate("detail_list/${it.id}") }
                    },
                    onEmptyList = { navController.navigate("list_error") }
                )
            }
            composable("detail_list/{taskId}") { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                taskId?.let { DetailListScreen(navController, it) }
            }
            composable("list_error") { ListErrorScreen(navController) }
        }
    }
}
