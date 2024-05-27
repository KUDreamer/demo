package com.example.demo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.example.demo.R
import com.example.demo.Routes

data class ListInfo(var name: String, var img: Int, var che: Boolean) {

}

private fun respone_text(blocks: List<MutableState<ListInfo>>, searchText: String) {
    blocks.forEach { block ->
        // 여기에서 block의 'name'을 검색 텍스트와 비교하여 업데이트할 수 있습니다.
        // 예시로는 단순 검색 로직을 사용합니다. 실제 조건은 요구 사항에 맞게 조정해야 합니다.
        block.value = block.value.copy(che = block.value.name.contains(searchText, ignoreCase = true))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FieldTop(navController: NavHostController, isActive: Boolean,blocks: List<MutableState<ListInfo>>) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val fieldTopHeight = screenHeight / 8  // 화면 높이의 1/8

    // 검색 텍스트 상태 관리
    var searchText by remember { mutableStateOf("") }

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
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    respone_text(blocks, searchText)//검색어 들어온다면 검색어 기반으로 8개 넣기
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
//클릭커블

@Composable
private fun small_Box(blockA: MutableState<ListInfo>, navController: NavHostController) {
    Column {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(
                    Color.Gray,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
                .clickable(onClick = { navController.navigate(Routes.DetailedRestaurant.route) }),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(data = blockA.value.img),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
private fun check_box_rest(blockA: MutableState<ListInfo>, blockB: MutableState<ListInfo>,navController: NavHostController,) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .size(140.dp)
                .background(Color.Red, RoundedCornerShape(20.dp))
        ) {
            small_Box(blockA,navController)  // 체크 상태 변경 함수 전달
        }
        Spacer(modifier = Modifier.width(40.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .size(140.dp)
                .background(Color.Red, RoundedCornerShape(20.dp))
        ) {
            small_Box(blockB,navController)  // 체크 상태 변경 함수 전달
        }
    }
}

@Composable
private fun menu_box(blocks: List<MutableState<ListInfo>>,navController: NavHostController) {


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
            // 첫 번째 행
            check_box_rest(blocks[0],blocks[1],navController)
            check_box_rest(blocks[2],blocks[3],navController)
            check_box_rest(blocks[4],blocks[5],navController)
            check_box_rest(blocks[6],blocks[7],navController)
            // 두 번째
        }
    }
}




@Composable
fun RestaurantList(navController: NavHostController) {
    // 초기 데이터 및 상태 정의
    val images = R.drawable.exres
    val blocks = remember {
        mutableStateListOf(
            mutableStateOf(ListInfo("1", images, false)),
            mutableStateOf(ListInfo("2", images, false)),
            mutableStateOf(ListInfo("3", images, false)),
            mutableStateOf(ListInfo("4", images, false)),
            mutableStateOf(ListInfo("5", images, false)),
            mutableStateOf(ListInfo("6", images, false)),
            mutableStateOf(ListInfo("7", images, false)),
            mutableStateOf(ListInfo("8", images, false)),
        )
    }
    val foods = listOf(
        "피자", "스테이크", "파스타", "샐러드", "김밥", "라멘", "치킨", "햄버거", "샌드위치", "불고기",
        "비빔밥", "초밥", "타코", "부리토", "두부", "죽", "김치찌개", "된장찌개", "갈비탕", "샤브샤브",
        "팟타이", "커리", "피쉬 앤 칩스", "탕수육", "만두", "오향장육", "파에야", "라자냐", "에그 베네딕트", "라따뚜이"
    )
    //respone_text(blocks,foods[2]) 처음에 여기 음식 랜덤으로 검색해서 block초기화


    // 체크된 항목의 수를 추적
    val checkedCount = remember { mutableStateOf(0) }

    // 체크 상태 업데이트 함수
    fun updateCheckState() {
        checkedCount.value = blocks.count { it.value.che }
    }

    // 사용자 인터페이스
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FieldTop(navController, checkedCount.value > 0,blocks) // 활성화 상태 전달
        menu_box(blocks,navController) // 체크 상태 업데이트 함수 전달
    }
}

