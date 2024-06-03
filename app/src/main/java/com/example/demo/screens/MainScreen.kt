package com.example.demo.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.demo.ApiService
import com.example.demo.RetrofitClient
import com.example.demo.Routes
import com.example.demo.Timeline
import com.example.demo.fetchNearPlace
import com.example.demo.fetchPlaceFromQuery
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, trip: Trip) {

    val currentDate = LocalDate.of(2024, 6, 20)

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = trip.title,
                            fontSize = 32.sp,
                            fontWeight = FontWeight(800)
                        )



                        // TODO: 지우기
                        Button(onClick = {
                            fetchPlaceFromQuery("Starbucks")
//                            val call = RetrofitClient.apiService.getNearPlace("감자탕")
//                            call.enqueue(object: Callback<String> {
//                                override fun onResponse(call: Call<String>, response: Response<String>) {
//                                    if(response.isSuccessful) {
//                                        Log.d("testtest", response.body().toString())
//                                    }
//                                    else {
//                                        Log.e("API_CALL", "Response not successful: ${response.code()}")
//                                    }
//                                }
//
//                                override fun onFailure(p0: Call<String>, p1: Throwable) {
//                                    Log.e("API_CALL", "Error: ${p1.message}")
//                                }
//                            })
                        }) {
                            Text("server")
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.MyTrip.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "home",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.DetailedSchedule.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Create,
                            contentDescription = "edit",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            trip.days.forEach { day ->
                var expanded by remember { mutableStateOf(false) }
                val daysDifference = ChronoUnit.DAYS.between(currentDate, day.date)
                val dDayText = when {
                    daysDifference == 0L -> "D-Day"
                    daysDifference < 0L -> "D+${-daysDifference}"
                    else -> "D-${daysDifference}"
                }
                val dDayColor = when (daysDifference.absoluteValue) {
                    0L -> colorPalette[1]
                    1L -> colorPalette[2]
                    2L -> colorPalette[3]
                    else -> colorPalette[4]
                }

                Row(
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(15.dp))
                    Card(
                        modifier = Modifier
                            .width(56.dp)
                            .height(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = dDayColor,
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = dDayText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = day.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                        fontSize = 28.sp,
                        fontWeight = FontWeight(500)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = if (expanded)
                            Icons.Default.KeyboardArrowDown
                        else
                            Icons.Default.KeyboardArrowUp,
                        contentDescription = "Toggle Icon"
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }

                if (expanded) {
                    Timeline(day.places)
                }
            }
        }
    }
}

@Composable
fun DayUnit() {

}