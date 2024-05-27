package com.example.demo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.demo.R
import com.example.demo.Routes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

data class Place(val name: String, val time: String?)
data class Day(val date: LocalDate, val places: List<Place>)
data class Trip(val title: String, val days: List<Day>)

val tripList = listOf(
    Trip(
        title = "부산 여행",
        days = listOf(
            Day(date = LocalDate.of(2024, 6, 19), places = listOf(
                Place(name = "영심이네 순두부찌개", time = null),
                Place(name = "베이커리 카페", time = null),
                Place(name = "메가박스", time = "18:00"),
                Place(name = "숙소", time = null)
            )),
            Day(date = LocalDate.of(2024, 6, 20), places = listOf(
                Place(name = "영심이네 순두부찌개", time = null),
                Place(name = "스타벅스 **점", time = null),
                Place(name = "공연", time = "16:00"),
                Place(name = "숙소", time = null)
            )),
            Day(date = LocalDate.of(2024, 6, 21), places = listOf(
                Place(name = "영심이네 순두부찌개", time = null),
                Place(name = "해수욕장", time = null),
                Place(name = "베이커리 카페", time = null),
                Place(name = "메가박스", time = "18:00"),
                Place(name = "숙소", time = null)
            )),
            Day(date = LocalDate.of(2024, 6, 22), places = listOf(
                Place(name = "영심이네 순두부찌개", time = null),
                Place(name = "베이커리 카페", time = null),
                Place(name = "스타벅스 **점", time = null),
                Place(name = "메가박스", time = "18:00"),
                Place(name = "숙소", time = null)
            )),
            Day(date = LocalDate.of(2024, 6, 23), places = listOf(
                Place(name = "영심이네 순두부찌개", time = null),
                Place(name = "베이커리 카페", time = null),
                Place(name = "스타벅스 **점", time = null),
                Place(name = "메가박스", time = "18:00"),
                Place(name = "숙소", time = null)
            )),
        )
    ),
    Trip(
        title = "엄마랑 강릉 여행",
        days = listOf(
            Day(date = LocalDate.of(2024, 9, 30), places = listOf(
                Place(name = "영심이네 순두부찌개", time = null),
                Place(name = "베이커리 카페", time = null),
                Place(name = "메가박스", time = "18:00"),
                Place(name = "숙소", time = null)
            ))
        )
    ),
    Trip(
        title = "제주도 힐링 여행",
        days = listOf(
            Day(date = LocalDate.of(2024, 12, 11), places = listOf(
                Place(name = "영심이네 순두부찌개", time = null),
                Place(name = "해수욕장", time = null),
                Place(name = "베이커리 카페", time = null),
                Place(name = "메가박스", time = "18:00"),
                Place(name = "숙소", time = null)
            )),
            Day(date = LocalDate.of(2024, 12, 12), places = listOf(
                Place(name = "영심이네 순두부찌개", time = null),
                Place(name = "베이커리 카페", time = null),
                Place(name = "스타벅스 **점", time = null),
                Place(name = "메가박스", time = "18:00"),
                Place(name = "숙소", time = null)
            )),
        )
    )
)

val colorPalette = listOf(
    Color(0xFFFF9730),
    Color(0xFFFAA955),
    Color(0xFFFFBD7B),
    Color(0xFFFFCC9A),
    Color(0xFFFFE4C9)
)

fun getRandomColor(): Color {
    return colorPalette[Random.nextInt(colorPalette.size)]
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTrip(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
//                modifier = Modifier.padding(top = 60.dp),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "내 일정",
                            fontSize = 32.sp,
                            fontWeight = FontWeight(800),
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )
                    }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Box(
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            Text(
//                                text = "내 일정",
//                                fontSize = 32.sp,
//                                fontWeight = FontWeight(800),
//                                modifier = Modifier.align(Alignment.Center)
//                            )
//                        }
//                    }
                },
                actions = {
                    IconButton(onClick = { /* 검색 화면? */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(tripList) { trip ->
                    TripItem(trip = trip)
//                    {
//                        selectedTrip = trip
//                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .clickable { navController.navigate(Routes.RestaurantList.route) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon( // LazyColumn이 길게 내려올 때 고려해서 수정
                    painter = painterResource(id = R.drawable.baseline_add_circle_outline_24),
                    contentDescription = "Add",
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "새로운 일정",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700))
            }
        }
    }
}

@Composable
fun TripItem(trip: Trip) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
//    fun TripItem(trip: Trip, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(66.dp),
        colors = CardDefaults.cardColors(containerColor = getRandomColor())
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.padding(start = 22.dp)
            ) {
                Text(text = trip.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )
                Text(text = "${getTripDateRange(trip)}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400))
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                contentDescription = "more",
                modifier = Modifier
                    .size(36.dp)
                    .clickable { isDropDownMenuExpanded = true }
            )
                DropdownMenu(
                    modifier = Modifier
                        .background(Color(0xFFFFE4C9)),
//                    .width(107.dp)
//                    .height(131.dp),
                    expanded = isDropDownMenuExpanded,
                    onDismissRequest = { isDropDownMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "편집",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500))
                                Icon(
                                    Icons.Outlined.Edit,
                                    contentDescription = "Edit",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        },
                        onClick = { }
                    )
                    Divider(color = Color(0xFFFAA955))
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("복사",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500))
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_content_copy_24),
                                    contentDescription = "copy",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        },
                        onClick = { }
                    )
                    Divider(color = Color(0xFFFAA955))
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("삭제",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(500))
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_delete_24),
                                    contentDescription = "delete",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        },
                        onClick = { }
                    )
                }

            Spacer(modifier = Modifier.width(15.dp))
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

fun getTripDateRange(trip: Trip): String? {
    val days = trip.days
    return when {
//        days.isEmpty() -> null
        days.size == 1 -> days[0].date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        else -> {
            val startDate = days[0].date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) // Trip에 날짜별로 정렬되도록 해야 함
            val endDate = days[days.size - 1].date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            "$startDate - $endDate"
        }
    }
}