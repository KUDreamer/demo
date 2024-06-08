package com.example.demo.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.demo.NavViewModel
import com.example.demo.Routes
import com.example.demo.fetchPlaceFromQuery
import kotlin.random.Random
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage
import com.example.demo.fetchgetPlaceInfo
import kotlinx.coroutines.launch
import com.example.demo.Result_view
import com.example.demo.Photo_view
import com.example.demo.DataModel_view



data class ListInfo(var name: String, var img: String, var address: String , var rate: Double)

private suspend fun respone_text(block: MutableState<ListInfo>, searchText: String, navViewModel: NavViewModel) {
    //println("aaaaaaaaaaaaaaa")

    fetchPlaceFromQuery(searchText, navViewModel)

    var json_s = navViewModel.fetchReturn
    println(json_s)
    if (json_s == null) {
        json_s = "{candidates=[{formatted_address=대한민국 서울특별시 광진구 화양동 48-25, name=함밝×파스타리코, photos=[{height=2250.0, html_attributions=[<a href=\"https://maps.google.com/maps/contrib/114142466491985332874\">Mavis Gyamfi</a>], photo_reference=AUGGfZkdWoso1lvi1-eYzq7W1m3ZnCw8G9FqcCCK_fH6S0XPZ4cFC9j8PWC-BH_U2MXT_K-yrwGTgnVPYqphVF_8F2PeeqLSGqVRCdAm6o8Ce4oXhzcQPDK2ClnwJHXv2SAOUP_POjD_1DEX6GHkzpOlwjjCmHdPTHoAWDwXrO2JChhmWh-x, width=4000.0}], rating=5.0}], status=OK}"
    }

    try {
        val addressRegex = "formatted_address=([^,]+)".toRegex()
        val addressMatch = addressRegex.find(json_s)
        val formattedAddress = addressMatch?.groups?.get(1)?.value ?: "No Address"
        // 예시 주소
        //대한민국 서울특별시 성동구 성수2가제3동 아차산로17길 11
       //println(formattedAddress)
        // Extract name
        val nameRegex = "name=([^,]+)".toRegex()
        val nameMatch = nameRegex.find(json_s)
        val name = nameMatch?.groups?.get(1)?.value ?: "No Name"
        //println(name)
        //예시 이름
        //앳모스피어 같은 그냥 이름
        // Extract photo URL (photo_reference)
        val photoRegex = "photo_reference=([^,}]+)".toRegex()
        val photoMatch = photoRegex.find(json_s)
        val photoUrl = (photoMatch?.groups?.get(1)?.value ?: "NoPhotoReference")
        val photoUrlA = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$photoUrl&key=AIzaSyAOWi8i0eZwjY2IXYe9-SCQNUdOE5dJcec"
        println(photoUrl)
        println(photoUrlA)
        // 예시 이미지
        // https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=AUGGfZnxOls39TwAZ4rMjkXdrsnpRQeFXOSU5FrsFO-xV6cBjTvmNV2DL-1oORfSqfas7Ck3U-HtahScWyFJcCGqg5xkOuqiFSYS9Ti829KDwnzVCovleGPHl1dLgRfEEjMKT3Bf3W87EixNTyCE9IxbwQ7VIr3Ig8ZYPxj-ixEiMgIVvbZ9
        // 이게 맞는지는 모르겠지만 일단 이리, 일단 이미지 너무 크지않게 400*400으로
        val ratingRegex = "rating=([\\d.]+)".toRegex()
        val ratingMatch = ratingRegex.find(json_s)
        val rating = ratingMatch?.groups?.get(1)?.value?.toDouble() ?: 0.0
        //println(rating)
        //예시 별점 4.5 같이 소스점 한자리까지
        val widthRegex = "width=([\\d.]+)".toRegex()
        val widthMatch = widthRegex.find(json_s)
        val width = widthMatch?.groups?.get(1)?.value?.toDouble() ?: 0.0
       // println(width)

        // Extract height
        val heightRegex = "height=([\\d.]+)".toRegex()
        val heightMatch = heightRegex.find(json_s)
        val height = heightMatch?.groups?.get(1)?.value?.toDouble() ?: 0.0
        //println(height)

        fetchgetPlaceInfo(photoUrl,200 ,navViewModel)
        var url_pp = navViewModel.fetchReturn
        println(url_pp)
        Log.d("testtest", photoUrl)


        //Update the block with parsed data
        block.value = ListInfo(
            name = name,
            img = photoUrlA,
            address = formattedAddress,
            rate = rating
        )


    } catch (e: Exception) {
        //println("bbbbbbbbbbb")
        e.printStackTrace()
        Log.e("JSONParsingError", "Error parsing JSON response", e)
    }
}


fun modifySearchText(original: String, isis: Int): String {
    return original.map { char ->
        // Shift the character by `isis` positions in the ASCII table
        val shiftedChar = char + isis
        if (shiftedChar.toInt() > 126) {
            (shiftedChar - 95).toChar() // Wrap around to the beginning of printable characters
        } else {
            shiftedChar.toChar()
        }
    }.joinToString("")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FieldTop(
    navController: NavHostController,
    isActive: Boolean,
    blocks: List<MutableState<ListInfo>>,
    navViewModel: NavViewModel
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val fieldTopHeight = screenHeight / 8  // 화면 높이의 1/8

    // 검색 텍스트 상태 관리
    var searchText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope() // Get the coroutine scope

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(fieldTopHeight)
            .background(Color.White)
    ) {
        // 메뉴 버튼
        IconButton(
            onClick = { navController.navigate(Routes.MyTrip.route) },
            modifier = Modifier
                .offset(x = 16.dp, y = 40.dp)  // 좌표로 위치 지정
                .size(24.dp)  // 버튼 크기
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,  // '<' 아이콘
                contentDescription = "Back"
            )
        }

        // 검색 필드
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(fraction = 0.6f)  // TextField의 너비를 Box의 60%로 설정
                .padding(horizontal = 16.dp),
            enabled = true,
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 12.sp,
                color = Color.Black
            ),
            isError = false,
            singleLine = true,
            placeholder = {
                Text("검색", style = androidx.compose.ui.text.TextStyle(color = Color.Gray))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    coroutineScope.launch {
                        for (i in 0 until 8) {
                            val modifiedSearchText = modifySearchText(searchText, i)
                            respone_text(blocks[i], searchText, navViewModel)
                        }
                    }
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = Color.Black,
                focusedBorderColor = Color.Black,  // 포커스 상태에서 테두리 색상
                unfocusedBorderColor = Color.Black,  // 비포커스 상태에서 테두리 색상
                errorBorderColor = Color.Black  // 오류 상태에서의 테두리 색상
            )
        )
    }
}
fun send_info(blockA: MutableState<ListInfo>,navController: NavViewModel) {
    val resultView = Result_view().apply {
        address = blockA.value.address
        name = blockA.value.name
        rating = blockA.value.rate.toString()
        photo = Photo_view().apply {
            url = blockA.value.img
        }
    }

    val dataModelView = DataModel_view().apply {
        result = resultView
        status = "OK"
    }
    navController.setData(dataModelView)
}

@Composable
private fun small_Box(blockA: MutableState<ListInfo>, navController: NavHostController,navViewModel: NavViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(8.dp)
    ) {
        // 상단 텍스트 박스
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .background(
                    Color(0xFFFF9730),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = blockA.value.name, color = Color.Black)
        }
        // 하단 이미지 박스
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(
                    Color.Gray,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
                .clickable(onClick = {
                    send_info(blockA,navViewModel)
                    navController.navigate(Routes.DetailedRestaurant.route)
                }),
            contentAlignment = Alignment.Center
        ) {
            if (blockA.value.img.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(data = blockA.value.img),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Image", color = Color.White)
                }
            }
        }
    }
}


@Composable
private fun check_box_rest(blockA: MutableState<ListInfo>, blockB: MutableState<ListInfo>, navController: NavHostController,navViewModel: NavViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .size(140.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
        ) {
            small_Box(blockA, navController,navViewModel)  // 체크 상태 변경 함수 전달
        }
        Spacer(modifier = Modifier.width(40.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .size(140.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
        ) {
            small_Box(blockB, navController,navViewModel)  // 체크 상태 변경 함수 전달
        }
    }
}

@Composable
private fun menu_box(blocks: List<MutableState<ListInfo>>, navController: NavHostController,navViewModel: NavViewModel) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val fieldTopHeight = (screenHeight / 8) * 7  // 화면 높이의 7/8

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(fieldTopHeight)
            .background(Color.White)
            .padding(40.dp)  // 전체 Box의 패딩
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(40.dp),  // 열 내 요소 사이의 간격
            horizontalAlignment = Alignment.CenterHorizontally  // 모든 아이템을 가로 방향으로 중앙 정렬
        ) {
            check_box_rest(blocks[0], blocks[1], navController,navViewModel)
            check_box_rest(blocks[2], blocks[3], navController,navViewModel)
            check_box_rest(blocks[4], blocks[5], navController,navViewModel)
            check_box_rest(blocks[6], blocks[7], navController,navViewModel)
        }
    }
}

@Composable
fun RestaurantList(navController: NavHostController, navViewModel: NavViewModel) {
    // 초기 데이터 및 상태 정의
    val foods = listOf(
        "피자", "스테이크", "파스타", "샐러드", "김밥", "라멘", "치킨", "햄버거", "샌드위치", "불고기",
        "비빔밥", "초밥", "타코", "부리토", "두부", "죽", "김치찌개", "된장찌개", "갈비탕", "샤브샤브",
        "팟타이", "커리", "피쉬 앤 칩스", "탕수육", "만두", "오향장육", "파에야", "라자냐", "에그 베네딕트", "라따뚜이"
    )

    val blocks = remember {
        List(8) {
            val randomFood = foods[Random.nextInt(foods.size)]
            mutableStateOf(ListInfo(name = randomFood, img = "img_$randomFood.png", address = "",rate=0.0))
        }
    }

    val offList = Array(8) { "" }

    // 배열에 무작위 음식 추가


    LaunchedEffect(key1 = Unit) {
        for (i in offList.indices) {
            offList[i] = foods[Random.nextInt(foods.size)]
        }
        for (i in 0 until 8) {
            respone_text(blocks[i], offList[i], navViewModel)
        }
    }

    // 체크된 항목의 수를 추적
    val checkedCount = remember { mutableStateOf(0) }

    // 사용자 인터페이스
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FieldTop(navController, checkedCount.value > 0, blocks, navViewModel) // 활성화 상태 전달
        menu_box(blocks, navController,navViewModel) // 체크 상태 업데이트 함수 전달
    }
}
