package com.mitm.android.grocerysharedlist.presentation.ui.room_settings

import com.mitm.android.grocerysharedlist.model.Item

data class RoomSettingsStates(
    val roomID: String = "ID:",
    val roomIsExist: Boolean = true,
    val inputRoomId: String = "",
    val loading: Boolean = false,
    val navigatePopUp: Boolean = false,
)
