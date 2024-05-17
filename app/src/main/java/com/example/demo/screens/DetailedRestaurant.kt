package com.example.demo.screens
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.demo.ui.theme.DemoTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import com.example.demo.Routes

data class RestaurantData(
    val name: String,
    val rating: String,
    val reviewCount: Int,
    val phoneNumber: String,
    val hasPhoneNumber: Boolean,
    val imageUrl1: String,
    val imageUrl2: String,
    val imageUrl3: String,
    val imageUrl4: String,
    val operatingHoursText: String,
    val hasOperatingHours: Boolean,
    val hashtags: List<String>
)

@Composable
fun DetailedRestaurant(navController: NavHostController) {
    val restaurantData = fetchRestaurantData()

    DemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            if (restaurantData != null) {
                MainContent(
                    restaurantData = restaurantData,
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
        }
    }
}

private fun fetchRestaurantData(): RestaurantData? {
    val jsonString = """
        {
            "name": "마라비빔면&쇠고기",
            "rating": "2.6",
            "reviewCount": 107,
            "phoneNumber": "010-1234-1234",
            "hasPhoneNumber": true,
            "imageUrl1": "https://via.placeholder.com/170x180.png?text=Food+Image+1",
            "imageUrl2": "https://via.placeholder.com/170x85.png?text=Food+Image+2",
            "imageUrl3": "https://via.placeholder.com/85x200.png?text=Food+Image+3",
            "imageUrl4": "https://via.placeholder.com/85x200.png?text=Food+Image+4",
            "operatingHoursText": "평일\n09:00-13:00 브레이크 타임\n13:00 라스트 오더\n09:00-13:00 토요일\n09:00-13:00 일요일\n09:00-13:00 휴일\n09:00-13:00 그 외 쉬는날",
            "hasOperatingHours": true,
            "hashtags": ["#냉면", "#마라마라", "#마라마라기"]
        }
    """.trimIndent()

    return try {
        val jsonObject = JSONObject(jsonString)
        RestaurantData(
            name = jsonObject.getString("name"),
            rating = jsonObject.getString("rating"),
            reviewCount = jsonObject.getInt("reviewCount"),
            phoneNumber = jsonObject.getString("phoneNumber"),
            hasPhoneNumber = jsonObject.getBoolean("hasPhoneNumber"),
            imageUrl1 = jsonObject.getString("imageUrl1"),
            imageUrl2 = jsonObject.getString("imageUrl2"),
            imageUrl3 = jsonObject.getString("imageUrl3"),
            imageUrl4 = jsonObject.getString("imageUrl4"),
            operatingHoursText = jsonObject.getString("operatingHoursText"),
            hasOperatingHours = jsonObject.getBoolean("hasOperatingHours"),
            hashtags = jsonObject.getJSONArray("hashtags").let { array ->
                List(array.length()) { array.getString(it) }
            }
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun MainContent(restaurantData: RestaurantData, modifier: Modifier = Modifier, navController: NavHostController) {
    var isExpanded by remember { mutableStateOf(false) }

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
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.height(180.dp)
                        ) {
                            AsyncImage(
                                model = restaurantData.imageUrl2,
                                contentDescription = "음식 이미지",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(170.dp)
                                    .height(85.dp)
                                    .padding(bottom = 8.dp)
                            )
                            Row {
                                AsyncImage(
                                    model = restaurantData.imageUrl3,
                                    contentDescription = "음식 이미지",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(85.dp)
                                        .height(200.dp)
                                        .padding(end = 8.dp)
                                )
                                AsyncImage(
                                    model = restaurantData.imageUrl4,
                                    contentDescription = "음식 이미지",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(85.dp)
                                        .height(200.dp)
                                )
                            }
                        }
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
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        Button(
            onClick = {
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
                    text = "음식점 선택",
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
    DemoTheme {
        val sampleData = RestaurantData(
            name = "마라비빔면&쇠고기",
            rating = "2.6",
            reviewCount = 107,
            phoneNumber = "010-1234-1234",
            hasPhoneNumber = true,
            imageUrl1 = "https://via.placeholder.com/170x180.png?text=Food+Image+1",
            imageUrl2 = "https://via.placeholder.com/170x85.png?text=Food+Image+2",
            imageUrl3 = "https://via.placeholder.com/85x200.png?text=Food+Image+3",
            imageUrl4 = "https://via.placeholder.com/85x200.png?text=Food+Image+4",
            operatingHoursText = """
                평일
                09:00-13:00 브레이크 타임
                13:00 라스트 오더
                09:00-13:00 토요일
                09:00-13:00 일요일
                09:00-13:00 휴일
                09:00-13:00 그 외 쉬는날
            """.trimIndent(),
            hasOperatingHours = true,
            hashtags = listOf("#냉면", "#마라마라", "#마라마라기")
        )
        MainContent(restaurantData = sampleData, navController = rememberNavController())
    }
}

fun handlePhoneClick(phoneNumber: String) {
    // TODO: 전화번호 클릭 이벤트 처리 로직 추가
}
