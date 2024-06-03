package com.example.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.demo.db.Repository
import com.example.demo.db.TripDatabase
import com.example.demo.db.TripViewModel
import com.example.demo.db.TripViewModelFactory
import com.example.demo.ui.theme.DemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetProject()
                }
            }
        }
    }
}

@Composable
fun SetProject() {
    val context = LocalContext.current
    val tripdb = TripDatabase.getDatabase(context)
    val tripViewModel: TripViewModel =
        viewModel(factory = TripViewModelFactory(Repository(tripdb)))
    val navViewModel: NavViewModel = viewModel()

    LaunchedEffect(Unit) {
        tripViewModel.insertSampleData()
    }

    val navController = rememberNavController()
    NavGraph(navController, navViewModel, tripViewModel)
}