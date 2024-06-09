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
        tripViewModel.insertTrip(Trip(id = 1, title = "부산 여행", startDate = LocalDate.of(2024, 6, 8), endDate = LocalDate.of(2024, 6, 12)))
        tripViewModel.insertTrip(Trip(id = 2, title = "엄마랑 강릉 여행", startDate = LocalDate.of(2024, 9, 30), endDate = LocalDate.of(2024, 9, 30)))
        tripViewModel.insertTrip(Trip(id = 3, title = "제주도 힐링 여행", startDate = LocalDate.of(2024, 12, 11), endDate = LocalDate.of(2024, 12, 12)))


        tripViewModel.insertDay(Day(id = 1, tripId = 1, date = LocalDate.of(2024, 6, 8), dayOfWeek = LocalDate.of(2024, 6, 8).dayOfWeek))
        tripViewModel.insertDay(Day(id = 2, tripId = 1, date = LocalDate.of(2024, 6, 9), dayOfWeek = LocalDate.of(2024, 6, 9).dayOfWeek))
        tripViewModel.insertDay(Day(id = 3, tripId = 1, date = LocalDate.of(2024, 6, 10), dayOfWeek = LocalDate.of(2024, 6, 10).dayOfWeek))
        tripViewModel.insertDay(Day(id = 4, tripId = 1, date = LocalDate.of(2024, 6, 11), dayOfWeek = LocalDate.of(2024, 6, 11).dayOfWeek))
        tripViewModel.insertDay(Day(id = 5, tripId = 1, date = LocalDate.of(2024, 6, 12), dayOfWeek = LocalDate.of(2024, 6, 12).dayOfWeek))

        tripViewModel.insertDay(Day(id = 6, tripId = 2, date = LocalDate.of(2024, 9, 30), dayOfWeek = LocalDate.of(2024, 9, 30).dayOfWeek))

        tripViewModel.insertDay(Day(id = 7, tripId = 3, date = LocalDate.of(2024, 12, 11), dayOfWeek = LocalDate.of(2024, 12, 11).dayOfWeek))
        tripViewModel.insertDay(Day(id = 8, tripId = 3, date = LocalDate.of(2024, 12, 12), dayOfWeek = LocalDate.of(2024, 12, 12).dayOfWeek))


        tripViewModel.insertPlace(Place(dayId = 1, name = "부산역", route = "지하철+도보 | 23분"))
        tripViewModel.insertPlace(Place(dayId = 1, name = "영동밀면&돼지국밥", route = "지하철+도보 | 31분"))
        tripViewModel.insertPlace(Place(dayId = 1, name = "송도 해상 케이블 카", time = "13:00", route = "도보 | 5분"))
        tripViewModel.insertPlace(Place(dayId = 1, name = "송도 스카이 파크", route = "도보 | 13분"))
        tripViewModel.insertPlace(Place(dayId = 1, name = "홍유단 남포점", route = "버스 | 12분"))
        tripViewModel.insertPlace(Place(dayId = 1, name = "다이아몬드 타워", time = "19:30", route = "버스+지하철 | 23분"))
        tripViewModel.insertPlace(Place(dayId = 1, name = "파크하얏트 부산"))

        tripViewModel.insertPlace(Place(dayId = 2, name = "파크하얏트 부산", route = "버스+도보 | 14분"))
        tripViewModel.insertPlace(Place(dayId = 2, name = "까사 부사노 광안점", route = "도보 | 21분"))
        tripViewModel.insertPlace(Place(dayId = 2, name = "명품물회", route = "지하철+도보 | 22분"))
        tripViewModel.insertPlace(Place(dayId = 2, name = "해동용궁사", route = "지하철+도보 | 13분"))
        tripViewModel.insertPlace(Place(dayId = 2, name = "스카이라인루지 부산", time = "13:00", route = "지하철+버스 | 29분"))
        tripViewModel.insertPlace(Place(dayId = 2, name = "해운대시장", route = "도보 | 11분"))
        tripViewModel.insertPlace(Place(dayId = 2, name = "파크하얏트 부산"))

        tripViewModel.insertPlace(Place(dayId = 3, name = "파크하얏트 부산", route = "지하철+도보 | 11분"))
        tripViewModel.insertPlace(Place(dayId = 3, name = "해운대 달맞이길", route = "도보 | 26분"))
        tripViewModel.insertPlace(Place(dayId = 3, name = "밀바라기칼국수", route = "지하철+도보 | 28분"))
        tripViewModel.insertPlace(Place(dayId = 3, name = "블루라인파크 해변열차", route = "버스 | 12분"))
        tripViewModel.insertPlace(Place(dayId = 3, name = "송정해수욕장", route = "도보 | 12분"))
        tripViewModel.insertPlace(Place(dayId = 3, name = "분식집 유부부 해운대점", route = "지하철+도보 | 11분"))
        tripViewModel.insertPlace(Place(dayId = 3, name = "파크하얏트 부산"))

        tripViewModel.insertPlace(Place(dayId = 4, name = "파크하얏트 부산", route = "도보 | 9분"))
        tripViewModel.insertPlace(Place(dayId = 4, name = "리틀오스", route = "지하철+도보 | 12분"))
        tripViewModel.insertPlace(Place(dayId = 4, name = "광안리 해수욕장", route = "버스+지하철 | 11분"))
        tripViewModel.insertPlace(Place(dayId = 4, name = "디저트카페 바닷마을과자점", route = "지하철+도보 | 42분"))
        tripViewModel.insertPlace(Place(dayId = 4, name = "민락항", route = "도보 | 19분"))
        tripViewModel.insertPlace(Place(dayId = 4, name = "라멘집 류센소 광안점", route = "지하철+도보 | 13분"))
        tripViewModel.insertPlace(Place(dayId = 4, name = "파크하얏트 부산"))

        tripViewModel.insertPlace(Place(dayId = 5, name = "파크하얏트 부산", route = "지하철+도보 | 12분"))
        tripViewModel.insertPlace(Place(dayId = 5, name = "광복로패션거리", route = "도보 | 19분"))
        tripViewModel.insertPlace(Place(dayId = 5, name = "본전돼지국밥", route = "지하철+버스 | 19분"))
        tripViewModel.insertPlace(Place(dayId = 5, name = "산리오 러버스 클럽 해운대점", route = "도보 | 4분"))
        tripViewModel.insertPlace(Place(dayId = 5, name = "해리단길", route = "도보 | 14분"))
        tripViewModel.insertPlace(Place(dayId = 5, name = "부산역"))

        Log.d("sample data", "Sample data inserted")
    }

    NavGraph(navController, navViewModel, tripViewModel)
}