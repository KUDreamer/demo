package com.example.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.demo.screens.AddSchedule
import com.example.demo.screens.Date
import com.example.demo.screens.DetailedRestaurant
import com.example.demo.screens.MainScreen
import com.example.demo.screens.MyTrip
import com.example.demo.screens.NewTrip
import com.example.demo.screens.RestaurantList

sealed class Routes(val route: String) {
    object AddSchedule : Routes("AddSchedule")
    object Date : Routes("Date")
    object DetailedRestaurant : Routes("DetailedRestaurant")
    object MainScreen : Routes("MainScreen")
    object MyTrip : Routes("MyTrip")
    object NewTrip : Routes("NewTrip")
    object RestaurantList : Routes("RestaurantList")
}

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }

@Composable
fun NavGraph(navController: NavHostController, navViewModel: NavViewModel) {

    val startScreen = if (navViewModel.HasSchedule) {
        Routes.MainScreen.route
    } else {
        Routes.MyTrip.route
    }

    val navStoreOwner = rememberViewModelStoreOwner()

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {
        NavHost(navController = navController, startDestination = startScreen) {
            composable(route = Routes.AddSchedule.route) {
                AddSchedule(navController)
            }

            composable(route = Routes.Date.route) {
                Date(navController)
            }

            composable(route = Routes.DetailedRestaurant.route) {
                DetailedRestaurant(navController)
            }

            composable(route = Routes.MainScreen.route) {
                MainScreen(navController)
            }

            composable(route = Routes.MyTrip.route) {
                MyTrip(navController)
            }

            composable(route = Routes.NewTrip.route) {
                NewTrip(navController)
            }

            composable(route = Routes.RestaurantList.route) {
                RestaurantList(navController)
            }
        }
    }
}
