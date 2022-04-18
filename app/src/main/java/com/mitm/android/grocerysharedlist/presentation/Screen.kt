package com.mitm.android.grocerysharedlist.presentation

sealed class Screen(val route:String){
    object ItemListScreen:Screen("items_list")
    object RoomSettingsScreen:Screen("room_settings")
}