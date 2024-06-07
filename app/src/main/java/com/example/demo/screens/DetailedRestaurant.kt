package com.example.demo.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.demo.R
import com.example.demo.ui.theme.DemoTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.demo.NavViewModel
import com.example.demo.Routes
import com.example.demo.Photo_view
import com.example.demo.OpeningHours_view
import com.example.demo.Result_view
import com.example.demo.DataModel_view

data class RestaurantData(
    val name: String,
    val rating: String,
    val reviewCount: Int,
    val phoneNumber: String,
    val hasPhoneNumber: Boolean,
    val imageUrl1: String,
    val operatingHoursText: String,
    val hasOperatingHours: Boolean,
    val hashtags: List<String>,
    val address: String
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)

fun DetailedRestaurant(navController: NavHostController, navViewModel: NavViewModel = viewModel()) {
    // Example data to be used in NavViewModel
    val samplePhotos = navViewModel.getPhoto()

    val sampleOpeningHours = OpeningHours_view().apply {
        open_now = "true"
        weekday_text = listOf(
            "평일 09:00-13:00 브레이크 타임 13:00 라스트 오더",
            "09:00-13:00 토요일",
            "09:00-13:00 일요일",
            "09:00-13:00 휴일",
            "09:00-13:00 그 외 쉬는날"
        )
    }

    val sampleResult = Result_view().apply {
        address = "서울시 광진구 뭐시기 저시기"
        formatted_phone_number = "010-1234-1234"
        name = "마라비빔면&쇠고기"
        opening_hours = sampleOpeningHours
        photo = samplePhotos
        rating = "2.6"
    }

    val sampleDataModel = DataModel_view().apply {
        result = sampleResult
        status = "OK"
    }

    // Set sample data to ViewModel

    val real_DataModel = navViewModel.getData()

    navViewModel.setData(sampleDataModel)

    val restaurantData = navViewModel.dataModel.value?.result?.let {
        RestaurantData(
            name = it.name ?: "",
            rating = it.rating ?: "0.0",
            reviewCount = 107,
            phoneNumber = it.formatted_phone_number ?: "",
            hasPhoneNumber = it.formatted_phone_number != null,
            imageUrl1 = (it.photo.toString()),
            operatingHoursText = it.opening_hours?.weekday_text?.joinToString("\n") ?: "",
            hasOperatingHours = it.opening_hours != null,
            hashtags = listOf("#냉면", "#마라마라", "#마라마라기"),
            address = it.address.toString()
        )
    }

    DemoTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "") },
                    navigationIcon = {
                        BackButton(navController = navController)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            restaurantData?.let {
                MainContent(
                    restaurantData = it,
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun BackButton(navController: NavHostController) {
    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                navController.popBackStack()
            }
    )
}

@Composable
fun MainContent(restaurantData: RestaurantData, modifier: Modifier = Modifier, navController: NavHostController, navViewModel: NavViewModel = viewModel()) {
    var isExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = restaurantData.name,
                    fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                    fontWeight = FontWeight.W800,
                    fontSize = 15.sp,
                    lineHeight = 28.64.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row {
                        AsyncImage(
                            model = restaurantData.imageUrl1,
                            contentDescription = "음식 이미지",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(170.dp)
                                .height(180.dp)
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
            item {
                val rating = remember { restaurantData.rating.toFloatOrNull() ?: 0f }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            text = restaurantData.rating,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        val fullStars = rating.toInt()
                        val hasHalfStar = rating - fullStars >= 0.5
                        val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

                        Row {
                            for (i in 1..fullStars) {
                                Image(
                                    painter = painterResource(id = R.drawable.star_full),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            if (hasHalfStar) {
                                Image(
                                    painter = painterResource(id = R.drawable.star_half),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            for (i in 1..emptyStars) {
                                Image(
                                    painter = painterResource(id = R.drawable.star_empty),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(${restaurantData.reviewCount})",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                }
            }
            if (restaurantData.hasPhoneNumber) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.phone_img),
                            contentDescription = "전화번호 아이콘",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = restaurantData.phoneNumber,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.phone_ring_vector),
                            contentDescription = "전화 걸기 아이콘",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable { handlePhoneClick(restaurantData.phoneNumber) }
                        )
                    }
                }
            }
            if (restaurantData.hasOperatingHours) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { isExpanded = !isExpanded }
                        ) {
                            Text(
                                text = "영업중 09:00-13:00",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Icon(
                                painter = if (isExpanded) painterResource(id = R.drawable.keyboard_arrow_up) else painterResource(
                                    id = R.drawable.arrowdown
                                ),
                                contentDescription = "Expand/Collapse Icon",
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        if (isExpanded) {
                            Text(
                                text = restaurantData.operatingHoursText,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                                fontWeight = FontWeight.Normal,
                                color = Color.Black,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    restaurantData.hashtags.forEach { hashtag ->
                        Button(
                            onClick = { /* TODO: 해시태그 클릭 이벤트 처리 */ },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    0xFFEEEEEE
                                )
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = hashtag,
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                                fontWeight = FontWeight.Normal,
                            )
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location_on), // 위치 아이콘
                        contentDescription = "위치 아이콘",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = restaurantData.address,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clickable {
                                handleMapClick(
                                    "https://www.google.com/maps/place/%EC%B0%B8%EB%A7%9B%EC%A7%91/data=!4m10!1m2!2m1!1z66eb7KeR!3m6!1s0x357ca38cd1268333:0x9b7d8a1cb4a868ca!8m2!3d37.5656544!4d126.9647906!15sCgbrp5vsp5FaCCIG66eb7KeRkgETYmFyYmVjdWVfcmVzdGF1cmFudJoBI0NoWkRTVWhOTUc5blMwVkpRMEZuU1VOcGNXVXRSMWhuRUFF4AEA!16s%2Fg%2F11hz5vqk8_?entry=ttu",
                                    context
                                )
                            }
                            .size(width = 102.dp, height = 30.dp) // 크기 조정
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.rectangle_62), // 배경 설정
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.location_on), // 위치 아이콘 (필요한 경우 아이콘 리소스 변경)
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "지도보기",
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        Button(
            onClick = {
                val adding = navViewModel.getData()
                if (adding != null) {
                    navViewModel.addDataModelForDate(navViewModel.getCurrentDate().toString(),adding)
                }
                navController.navigate(Routes.Date.route)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(220.dp)
                .height(60.dp)
                .padding(bottom = 16.dp)
                .offset(x = 50.dp, y = -20.dp),
            shape = RoundedCornerShape(36.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9730))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "장소 선택",
                    fontSize = 14.sp,
                    lineHeight = 33.41.sp,
                    fontWeight = FontWeight.W300,
                    fontFamily = FontFamily(Font(R.font.pretandard_variable)),
                    color = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.arrowdown),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(17.59.dp, 13.41.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailedRestaurantPreview() {
    val navController = rememberNavController()
    val navViewModel: NavViewModel = viewModel()

    DemoTheme {
        DetailedRestaurant(navController = navController, navViewModel = navViewModel)
    }
}

fun handlePhoneClick(phoneNumber: String) {
    // TODO: 전화번호 클릭 이벤트 처리 로직 추가
}

fun handleMapClick(url: String, context: Context) {
    // Create an intent using the provided URL
    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    mapIntent.setPackage("com.google.android.apps.maps")
    if (mapIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(mapIntent)
    } else {
        // If Google Maps app is not installed, open with web browser
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(webIntent)
    }
}
