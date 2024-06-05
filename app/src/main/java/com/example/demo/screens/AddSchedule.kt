package com.example.demo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.demo.NavViewModel
import com.example.demo.Routes



@Composable
fun AddSchedule(navController: NavHostController, navViewModel: NavViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "일정 추가",
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold)
        Button(onClick = {
            navController.navigate(Routes.MainScreen.route)
        }){
            Text(text = "MainScreen")
        }
    }
}