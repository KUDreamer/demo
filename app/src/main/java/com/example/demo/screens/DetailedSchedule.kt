package com.example.demo.screens

import com.example.demo.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// 폰트 적용을 위한 설정
private val pretendard_family = FontFamily(
    Font(R.font.ljw_pretendard_bold, FontWeight.Bold),
    Font(R.font.ljw_pretendard_extrabold, FontWeight.ExtraBold),
    Font(R.font.ljw_pretendard_extralight, FontWeight.ExtraLight),
    Font(R.font.ljw_pretendard_light, FontWeight.Light),
    Font(R.font.ljw_pretendard_medium, FontWeight.Medium),
    Font(R.font.ljw_pretendard_regular, FontWeight.W100), // 얘가 regular
    Font(R.font.ljw_pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.ljw_pretendard_thin, FontWeight.Thin),
)

private data class main_item(
    var title: String,
    var type: String,
    var rate_mean: String,
    var rate_num: String,
    var location: String,
    var details: String,
    var image_list: List<String>
)

private data class filter_item(var text: String, var isChecked: Boolean)
private data class drawer_item(
    var title: String,
    var type: String,
    var rate_mean: String,
    var rate_num: String,
    var isPinned: Boolean,
    var start_date: String,
    var end_date: String
)

// 서버로 부터 받은 데이터 가공
private fun process_date(): List<main_item> {
    return listOf<main_item>(
        main_item(
            "제목",
            "여행지",
            "1.7",
            "(120)",
            "서울특별시 가나구 나다동",
            "대충 설명",
            listOf(
                "https://i.namu.wiki/i/FFtbPbO7JwsuLVt7-QwnhSSOhBVhylDgNulplfT-r7bQPhpQGISTPQmGRJIU9vrHMQOPiuLEFo2IcD1GfbfSbFfti4eeJjy2mEhcQH7zKJa2eeTx06fGe7YZL7wMaXtrlNFZ1mtPJdQPQ0Vx5CEJ9g.webp",
                "https://i.namu.wiki/i/bz5xFDUpOKn3HOb7cQo-UX2dEA5dRQls5PN9ZlPQSSY7R9ovCn0aWgJjrkJO7X8X53OVlSYc6o9v12KF5nyUJIUi43nWcDRdpxiMhlUNBm6y3052KOHWhVz63nymOV0HRDwemfnaSFpeuqiuuuuGFA.webp",
                "https://i.namu.wiki/i/JaxZxaiYd-07iyjOUZnpBKxeeOHMATvpVySNcU_0cne3QHuNp-4S_2bnUaqsWX_07Y7ad9_OiCCHltITT6NXd1cM4ML4psaUrm3ZnEtGJIFyDzwx8U2HkcQR6akgMSqhU9jL9vog3BlDePZHhwBeyA.webp"
            )
        ),
        main_item(
            "제목2",
            "여행지",
            "3.8",
            "(120)",
            "서울특별시 일이구 삼사동",
            "대충 설명일듯",
            listOf(
                "https://i.namu.wiki/i/FFtbPbO7JwsuLVt7-QwnhSSOhBVhylDgNulplfT-r7bQPhpQGISTPQmGRJIU9vrHMQOPiuLEFo2IcD1GfbfSbFfti4eeJjy2mEhcQH7zKJa2eeTx06fGe7YZL7wMaXtrlNFZ1mtPJdQPQ0Vx5CEJ9g.webp",
                "https://i.namu.wiki/i/bz5xFDUpOKn3HOb7cQo-UX2dEA5dRQls5PN9ZlPQSSY7R9ovCn0aWgJjrkJO7X8X53OVlSYc6o9v12KF5nyUJIUi43nWcDRdpxiMhlUNBm6y3052KOHWhVz63nymOV0HRDwemfnaSFpeuqiuuuuGFA.webp",
                "https://i.namu.wiki/i/JaxZxaiYd-07iyjOUZnpBKxeeOHMATvpVySNcU_0cne3QHuNp-4S_2bnUaqsWX_07Y7ad9_OiCCHltITT6NXd1cM4ML4psaUrm3ZnEtGJIFyDzwx8U2HkcQR6akgMSqhU9jL9vog3BlDePZHhwBeyA.webp"
            )
        )
    )
}

@Composable
private fun simpleIcon(id: Int, size: Dp) {
    Icon(
        painter = painterResource(id = id),
        contentDescription = "",
        modifier = Modifier.size(size)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun detailedScheduleMain() {

    // 일정 이름
    var plan_name by remember {
        mutableStateOf("일정 이름 예시")
    }
    // 검색어
    var search_words by remember {
        mutableStateOf("")
    }

    var filter_list by remember {
        mutableStateOf(
            listOf<filter_item>(
                filter_item("여행지", false),
                filter_item("숙소", false),
                filter_item("식당", false)
            )
        )
    }

    var main_items by remember {
        mutableStateOf(listOf<main_item>())
    }

    val data_list = process_date()


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(30.dp))
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // 맨위
                Icon(
                    painter = painterResource(id = R.drawable.ljw_outline_home_96),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
//                TextField(value = plan_name,
//                    textStyle = TextStyle.Default.copy(fontFamily = pretendard_family, fontWeight = FontWeight.ExtraBold, fontSize = 32.sp),
//                    modifier = Modifier
//                        .padding(14.dp, 0.dp, 14.dp, 0.dp)
//                        .wrapContentSize(),
//                    onValueChange = { it -> plan_name = it})
                BasicTextField(value = plan_name,
                    textStyle = TextStyle.Default.copy(
                        fontFamily = pretendard_family,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp
                    ),
                    modifier = Modifier
                        .padding(14.dp, 0.dp, 14.dp, 0.dp)
                        .wrapContentSize()
                        .width(261.dp)
                        .drawBehind {
                            val y = size.height

                            drawLine(
                                color = Color.Black,
                                Offset(0f, y),
                                Offset(size.width, y),
                                strokeWidth = 5f
                            )
                        },
                    onValueChange = { it -> plan_name = it }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ljw_outline_save_96),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                // 검색어 부분
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .wrapContentSize()
                        .width(342.dp)
                ) {
                    val hintVisibility by remember {
                        derivedStateOf {
                            search_words.isEmpty()
                        }
                    }
                    if (hintVisibility) {
                        // placeholder용 Text
                        Text(
                            text = "어떤 이유",
                            modifier = Modifier
                                .alpha(0.5f)
                                .fillMaxWidth()
                        )
                    }
                    BasicTextField(
                        value = search_words,
                        textStyle = TextStyle.Default.copy(
                            fontFamily = pretendard_family,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier
                            .width(342.dp)
                            .height(25.dp)
                            .drawBehind {
                                val y = size.height

                                drawLine(
                                    color = Color.Black,
                                    Offset(0f, y),
                                    Offset(size.width, y),
                                    strokeWidth = 2f
                                )
                            },
                        onValueChange = { it -> search_words = it })
                    simpleIcon(id = R.drawable.ljw_outline_search_96, size = 24.dp)
                }
            }
            Row() {
                // 필터

                for (it in filter_list) {
                    filterItems(it = it)
                }

            }
        }
        Spacer(modifier = Modifier.height(27.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(27.dp)) {
            // 커스텀 아이템 들어가기
            itemsIndexed(items = data_list) {index, it->
                mainItem(it = it)
                Spacer(modifier = Modifier.height(27.dp))
                if(index < data_list.lastIndex) {
                    Divider(color = colorResource(id = R.color.text_gray2), modifier = Modifier.width(342.dp))
                }
            }
        }


    }

}

@Composable
private fun filterItems(it: filter_item) {
    if (it.isChecked) {
        Text(
            text = it.text,
            color = colorResource(id = R.color.palette5),
            fontFamily = pretendard_family,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(0.dp, 6.dp, 8.dp, 0.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.palette1),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(8.dp, 6.dp, 8.dp, 6.dp)
                .background(color = colorResource(id = R.color.palette1))
        )
    } else {
        Text(
            text = it.text,
            color = Color.Black,
            fontFamily = pretendard_family,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(0.dp, 6.dp, 8.dp, 0.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.palette1),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(8.dp, 6.dp, 8.dp, 6.dp)
                .background(color = Color.White)
        )
    }
}

@Composable
private fun mainItem(it: main_item) {
    // title, type, location, details, images
//    val title = "제목 예시"
//    val type = "타입 예시"
//    val rate_mean = "1.7" // 평균 별점
//    val rate_num = "(12)" // 리뷰수 예시
//    val location = "주소 예시"
//    val details = "설명 예시"
//    val image_list = listOf<String>() // 이미지 리스트 예시, 링크 받아와서 asyncimage하는 것으로 일단 처리
    val title = it.title
    val type = it.type
    val rate_mean = it.rate_mean
    val rate_num = it.rate_num
    val location = it.location
    val details = it.details
    val image_list = it.image_list

    Column() {
        Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.width(304.dp)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = pretendard_family,
                    color = colorResource(R.color.hyperlink),
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 2.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp)) {
                    Text(
                        text = type,
                        fontWeight = FontWeight.W100,
                        fontSize = 12.sp,
                        fontFamily = pretendard_family,
                        color = colorResource(R.color.text_gray)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box( // 중간의 원
                        contentAlignment = Alignment.Center,
                        content = {},
                        modifier = Modifier
                            .size(4.dp)
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .background(Color.Black, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = rate_mean,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        fontFamily = pretendard_family
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ljw_round_star_rate_96),
                        contentDescription = "",
                        tint = colorResource(id = R.color.rate_yellow),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = rate_num,
                        fontWeight = FontWeight.W100,
                        fontSize = 10.sp,
                        fontFamily = pretendard_family,
                        color = colorResource(id = R.color.text_gray2)
                    )
                }
                Text(
                    text = location,
                    fontWeight = FontWeight.W100,
                    fontSize = 12.sp,
                    fontFamily = pretendard_family,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
                )
                Text(
                    text = details,
                    fontWeight = FontWeight.W100,
                    fontSize = 12.sp,
                    fontFamily = pretendard_family
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ljw_outline_add_circle_outline_96), 
                contentDescription = "", 
                tint = colorResource(id = R.color.palette1),
                modifier = Modifier.size(48.dp))
        }

//        LazyRow() { // 이미지 들어가는 부분
//            items(image_list) { it ->
//                AsyncImage(model = it, contentDescription = "")
//            }
//        }

    }
}

@Composable
private fun Drawer() {

}

@Composable
fun drawerItemsDate() {
    // TODO: 날짜 생성기준
    Row() {
        simpleIcon(id = R.drawable.ljw_baseline_date_range_96, size = 32.dp)
        Text(
            text = "2024년 01월 01일",
            fontWeight = FontWeight.Bold,
            fontFamily = pretendard_family,
            fontSize = 28.sp
        )
    }
}

@Composable
private fun drawerItems(it: drawer_item) {
    // parameter 결정, drawer에 있는 아이템을 따로 관리하는 리스트가 있어야 할듯
    var image_id: Int
    Row() {
        simpleIcon(id = R.drawable.ljw_baseline_drag_indicator_96, size = 32.dp)
        Column() {
            Text(
                text = it.title,
                fontFamily = pretendard_family,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Row() {

            }
        }

        if (it.isPinned) {
            image_id = R.drawable.ljw_baseline_push_pin_96
        } else {
            image_id = R.drawable.ljw_outline_push_pin_96
        }

        Icon(
            painter = painterResource(id = image_id),
            contentDescription = "",
            modifier = Modifier.size(32.dp)
        )
        simpleIcon(id = R.drawable.ljw_baseline_remove_circle_outline_96, size = 48.dp)
    }
}