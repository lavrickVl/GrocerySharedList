package com.mitm.android.grocerysharedlist.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.ItemsListScreen
import com.mitm.android.grocerysharedlist.presentation.ui.room_settings.RoomSettingsScreen

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ItemListScreen.route) {

        composable(
            Screen.ItemListScreen.route,
        ) {
            ItemsListScreen(navController = navController)
        }

        composable(Screen.RoomSettingsScreen.route) {
            RoomSettingsScreen(navController)
        }
    }
}



