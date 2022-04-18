package com.mitm.android.grocerysharedlist.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.ItemsListScreen

@ExperimentalMaterialApi
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ItemListScreen.route ){

        composable(Screen.ItemListScreen.route){
            ItemsListScreen(navController = navController)
        }

        composable(Screen.RoomSettingsScreen.route){

        }
    }
}



