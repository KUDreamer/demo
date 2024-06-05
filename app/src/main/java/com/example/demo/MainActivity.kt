package com.example.demo

import android.os.Bundle
import android.util.Log
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
import com.example.demo.db.Day
import com.example.demo.db.Place
import com.example.demo.db.Repository
import com.example.demo.db.Trip
import com.example.demo.db.TripDatabase
import com.example.demo.db.TripViewModel
import com.example.demo.db.TripViewModelFactory
import com.example.demo.ui.theme.DemoTheme
import java.time.LocalDate

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
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        tripViewModel.insertTrip(Trip(id = 1, title = "부산 여행", startDate = LocalDate.of(2024, 6, 4), endDate = LocalDate.of(2024, 6, 8)))
        tripViewModel.insertTrip(Trip(id = 2, title = "엄마랑 강릉 여행", startDate = LocalDate.of(2024, 9, 30), endDate = LocalDate.of(2024, 9, 30)))
        tripViewModel.insertTrip(Trip(id = 3, title = "제주도 힐링 여행", startDate = LocalDate.of(2024, 12, 11), endDate = LocalDate.of(2024, 12, 12)))


        tripViewModel.insertDay(Day(id = 1, tripId = 1, date = LocalDate.of(2024, 6, 4), dayOfWeek = LocalDate.of(2024, 6, 4).dayOfWeek))
        tripViewModel.insertDay(Day(id = 2, tripId = 1, date = LocalDate.of(2024, 6, 5), dayOfWeek = LocalDate.of(2024, 6, 5).dayOfWeek))
        tripViewModel.insertDay(Day(id = 3, tripId = 1, date = LocalDate.of(2024, 6, 6), dayOfWeek = LocalDate.of(2024, 6, 6).dayOfWeek))
        tripViewModel.insertDay(Day(id = 4, tripId = 1, date = LocalDate.of(2024, 6, 7), dayOfWeek = LocalDate.of(2024, 6, 7).dayOfWeek))
        tripViewModel.insertDay(Day(id = 5, tripId = 1, date = LocalDate.of(2024, 6, 8), dayOfWeek = LocalDate.of(2024, 6, 8).dayOfWeek))

        tripViewModel.insertDay(Day(id = 6, tripId = 2, date = LocalDate.of(2024, 9, 30), dayOfWeek = LocalDate.of(2024, 9, 30).dayOfWeek))

        tripViewModel.insertDay(Day(id = 7, tripId = 3, date = LocalDate.of(2024, 12, 11), dayOfWeek = LocalDate.of(2024, 12, 11).dayOfWeek))
        tripViewModel.insertDay(Day(id = 8, tripId = 3, date = LocalDate.of(2024, 12, 12), dayOfWeek = LocalDate.of(2024, 12, 12).dayOfWeek))


        tripViewModel.insertPlace(Place(id = 1, dayId = 1, name = "영심이네 순두부찌개"))
        tripViewModel.insertPlace(Place(id = 2, dayId = 1, name = "베이커리 카페"))
        tripViewModel.insertPlace(Place(id = 3, dayId = 1, name = "메가박스", time = "18:00"))
        tripViewModel.insertPlace(Place(id = 4, dayId = 1, name = "숙소"))

        tripViewModel.insertPlace(Place(id = 5, dayId = 2, name = "영심이네 순두부찌개"))
        tripViewModel.insertPlace(Place(id = 6, dayId = 2, name = "스타벅스 **점"))

        tripViewModel.insertPlace(Place(id = 7, dayId = 3, name = "해수욕장"))
        tripViewModel.insertPlace(Place(id = 8, dayId = 3, name = "베이커리 카페"))
        tripViewModel.insertPlace(Place(id = 9, dayId = 3, name = "숙소"))

        tripViewModel.insertPlace(Place(id = 10, dayId = 4, name = "영심이네 순두부찌개"))
        tripViewModel.insertPlace(Place(id = 11, dayId = 4, name = "베이커리 카페"))
        tripViewModel.insertPlace(Place(id = 12, dayId = 4, name = "메가박스", time = "18:00"))
        tripViewModel.insertPlace(Place(id = 13, dayId = 4, name = "숙소"))

        tripViewModel.insertPlace(Place(id = 14, dayId = 5, name = "영심이네 순두부찌개"))
        tripViewModel.insertPlace(Place(id = 15, dayId = 5, name = "베이커리 카페"))
        tripViewModel.insertPlace(Place(id = 16, dayId = 5, name = "메가박스", time = "18:00"))
        tripViewModel.insertPlace(Place(id = 17, dayId = 5, name = "숙소"))

        Log.d("sample data", "Sample data inserted")
    }

    NavGraph(navController, navViewModel, tripViewModel)
}