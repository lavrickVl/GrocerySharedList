package com.mitm.android.grocerysharedlist.presentation.ui.room_settings

import androidx.compose.ui.focus.FocusState
import com.mitm.android.grocerysharedlist.model.Item

sealed class RoomSettingsEvent{
    object UpdateRoomID: RoomSettingsEvent()
    object RoomIsNotExist: RoomSettingsEvent()
    object SetHomeRoomID: RoomSettingsEvent()
    data class EditInputRoomID(val inputText: String): RoomSettingsEvent()
    data class ChangeContentFocus(val focusState: FocusState): RoomSettingsEvent()
}
