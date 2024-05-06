package com.example.demo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.demo.Routes

@Composable
fun MainScreen(navController: NavHostController) {
    Row {
        Icon(imageVector = Icons.Default.Home,
            contentDescription = "Home",
            modifier = Modifier.size(30.dp)
                    .clickable {
                navController.navigate(Routes.MyTrip.route)
            }
        )
        Text(text = "MainScreen",
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}