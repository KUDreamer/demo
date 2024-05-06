package com.example.demo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.demo.Routes

@Composable
fun AddSchedule(navController: NavHostController) {
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