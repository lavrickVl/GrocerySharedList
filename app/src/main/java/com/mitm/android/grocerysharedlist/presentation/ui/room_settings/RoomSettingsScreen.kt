package com.mitm.android.grocerysharedlist.presentation.ui.room_settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun RoomSettingsScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(Color.Magenta)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Button(onClick = {
            navController.popBackStack()
        }) {}
    }
}