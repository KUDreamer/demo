package com.example.demo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.demo.Routes
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale
import androidx.compose.ui.text.style.TextAlign  // TextAlign을 위한 임포트
import java.time.format.TextStyle  // TextStyle을 위한 임포트


@Composable
private fun SimpleCalendar(selectedDates: List<LocalDate>, onSelectDate: (List<LocalDate>) -> Unit) {
    var yearMonth by remember { mutableStateOf(YearMonth.now()) }
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstOfMonth = yearMonth.atDay(1)
    val dayOfWeekOfFirst = firstOfMonth.dayOfWeek.value
    val dates = mutableListOf<LocalDate?>()

    for (i in 1 until dayOfWeekOfFirst) {
        dates.add(null)
    }

    for (day in 1..daysInMonth) {
        dates.add(LocalDate.of(yearMonth.year, yearMonth.month, day))
    }

    // 선택된 날짜와 범위를 강조하는 색상 관리
    val highlightedDates = mutableSetOf<LocalDate>()
    val rangeDates = mutableSetOf<LocalDate>()
    when (selectedDates.size) {
        1 -> {
            highlightedDates.add(selectedDates[0])
        }
        2 -> {
            val (start, end) = selectedDates.sorted()
            var currentDate = start.plusDays(1)
            while (!currentDate.isAfter(end.minusDays(1))) {
                rangeDates.add(currentDate)
                currentDate = currentDate.plusDays(1)
            }
            highlightedDates.addAll(listOf(start, end))  // Start and end dates are added to highlightedDates
        }
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { yearMonth = yearMonth.minusMonths(1) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black // 텍스트 색상 설정
                )
            ) {
                Text("<")
            }

            Text(
                text = "${yearMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${yearMonth.year}",
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { yearMonth = yearMonth.plusMonths(1) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black // 텍스트 색상 설정
                )
            ) {
                Text(">")
            }
        }
    }


        Row(modifier = Modifier.fillMaxWidth().background(Color.White)) {
            DayOfWeek.values().forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    modifier = Modifier.weight(1f).padding(4.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.background(Color.White).size(width = 411.dp, height = 250.dp)
        ) {
            items(dates) { date ->
                date?.let {
                    val isHighlighted = highlightedDates.contains(date)
                    val isRange = rangeDates.contains(date)
                    DayDateBox(date, isHighlighted, isRange) {
                        if (selectedDates.contains(date)) {
                            onSelectDate(selectedDates.toMutableList().apply { remove(date) })
                        } else {
                            if (selectedDates.size < 2) {
                                onSelectDate((selectedDates + date).sorted())
                            }
                        }
                    }
                } ?: Spacer(modifier = Modifier.size(40.dp))
            }
        }
    }


@Composable
private fun DayDateBox(date: LocalDate, isHighlighted: Boolean, isRange: Boolean, onClick: () -> Unit) {
    val size = 40.dp
    val backgroundColor = when {
        isHighlighted -> Color(0xFFFF9730)
        isRange -> Color(0x4DFF9730)
        else -> Color.Transparent
    }
    val borderSize = 1.dp
    val shape = when {
        isRange -> RoundedCornerShape(0.dp)  // 중간 날짜는 원형
        else -> CircleShape  // 선택된 날짜는 완전한 사각형
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .offset(if (isRange) (-1).dp else 0.dp, 0.dp)  // 원형 날짜를 약간 겹치게
            .background(color = backgroundColor, shape = shape)
            .border(borderSize, if (isHighlighted || isRange) backgroundColor else Color.White, shape)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = if (isHighlighted || isRange) Color.Black else Color.Black
        )
    }
}

@Composable
private fun top_filed(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()  // 전체 너비를 사용
            .height(120.dp)  // 높이 지정
            .background(Color.White)  // 배경색 설정
    ) {
        // 버튼 위치 조정
        Button(
            onClick = { navController.navigate(Routes.RestaurantList.route) },
            modifier = Modifier
                .offset(x = 16.dp, y = 40.dp)  // 좌표로 위치 지정
                .size(24.dp)  // 버튼 크기
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu"
            )
        }

        // 텍스트 위치 조정
        Text(
            text = "날짜",
            color = Color.Black,
            modifier = Modifier
                .offset(x = (411.dp - 90.dp) / 2, y = 40.dp)  // 중앙 정렬을 위해 계산된 위치
                .padding(horizontal = 16.dp)  // 좌우 패딩
            , fontSize = 32.sp
        )
    }
}

@Composable
private fun textfiled1(fixedText : String) {
    BasicTextField(
        value = "시작날짜"+fixedText,
        onValueChange = {},  // 변경되지 않도록 비워둠
        readOnly = true,  // 읽기 전용으로 설정
        modifier = Modifier
            .padding(20.dp)  // 약간의 패딩 추가
            .fillMaxWidth()
            .height(80.dp)
            .border(color = Color.White, width = 2.dp)  // 테두리 두께 조정
            .background(color = Color.White),  // 배경색 추가
        textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontSize = 30.sp),  // 텍스트 스타일 적용
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,  // 중앙 정렬
                modifier = Modifier.fillMaxWidth().padding(16.dp)  // 최대 너비를 채우고 패딩 적용
            ) {
                innerTextField()  // 텍스트 필드 호출
            }
        }
    )
}

@Composable
private fun textfiled2(fixedText: String) {

    BasicTextField(
        value = "종료날짜"+fixedText,
        onValueChange = {},  // 변경되지 않도록 비워둠
        readOnly = true,  // 읽기 전용으로 설정
        modifier = Modifier
            .padding(20.dp)  // 약간의 패딩 추가
            .fillMaxWidth()
            .height(80.dp)
            .border(color = Color.White, width = 2.dp)  // 테두리 두께 조정
            .background(color = Color.White),  // 배경색 추가
        textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontSize = 30.sp),  // 텍스트 스타일 적용
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.Center,  // 중앙 정렬
                modifier = Modifier.fillMaxWidth().padding(16.dp)  // 최대 너비를 채우고 패딩 적용
            ) {
                innerTextField()  // 텍스트 필드 호출
            }
        }
    )
}

@Composable
private fun to_make_schedul(
    navController: NavHostController, isButtonEnabled: Boolean,
    selectedDates: List<LocalDate>
) {
    val buttonColor = if (isButtonEnabled) Color(0xFFFF9730) else Color.Gray  // 버튼 활성화 상태에 따른 색상 설정

    Button(
        onClick = {
            if (isButtonEnabled) {
                navController.navigate(Routes.DetailedRestaurant.route)  // 버튼이 활성화 상태일 때만 네비게이션 실행
                //navController.navigate(Routes.DetailedRestaurant.route,selectedDates)
                //selectedDates날짜 정보 넘김
            }
        },
        modifier = Modifier
            .width(200.dp)
            .height(50.dp)
            .offset(x = 0.dp, y = 20.dp),
        colors = ButtonDefaults.buttonColors( buttonColor)  // 버튼 색상 설정
    ) {
        Text(text = "일정 만들기")
        Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
    }
}

@Composable
 fun Date(navController: NavHostController) {
    var selectedDates by remember { mutableStateOf<List<LocalDate>>(emptyList()) }
    val isButtonEnabled = selectedDates.isNotEmpty()

    Column(
        modifier = Modifier.fillMaxSize() ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        top_filed(navController)
        SimpleCalendar(selectedDates) { dates ->
            selectedDates = dates
        }
        textfiled1(if (selectedDates.size > 0) (selectedDates[0]).toString() else "0000-00-00")
        textfiled2(if (selectedDates.size > 1) selectedDates[1].toString() else "0000-00-00")
        to_make_schedul(navController, isButtonEnabled,selectedDates)
    }
}
