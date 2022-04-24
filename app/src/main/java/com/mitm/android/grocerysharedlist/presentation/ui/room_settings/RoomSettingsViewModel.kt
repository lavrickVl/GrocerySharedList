package com.mitm.android.grocerysharedlist.presentation.ui.room_settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mitm.android.grocerysharedlist.core.AppSettings
import com.mitm.android.grocerysharedlist.domain.repository.RepositoryGrocery
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.ListEvent
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.ListStates
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.InputItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class RoomSettingsViewModel @Inject constructor(
    private val repository: RepositoryGrocery,
    private val appSettings: AppSettings
) : ViewModel() {

    private val roomID = appSettings.getRoomID() ?: ""
    private val _state = mutableStateOf<RoomSettingsStates>(
        RoomSettingsStates(
            roomID = roomID,
            inputRoomId = roomID
        )
    )
    val roomState: State<RoomSettingsStates> = _state


    private val _roomIdContent = mutableStateOf(
        InputItemState(
            hint = "Enter the room"
        )
    )
    val roomIdContent: State<InputItemState> = _roomIdContent

    @ExperimentalCoroutinesApi
    fun onEvent(roomEvents: RoomSettingsEvent) {

        when (roomEvents) {

            is RoomSettingsEvent.EditInputRoomID -> {
                _state.value = roomState.value.copy(
                    inputRoomId = roomEvents.inputText
                )
            }


            is RoomSettingsEvent.ChangeContentFocus -> {
                _roomIdContent.value = _roomIdContent.value.copy(
                    isHintVisible = !roomEvents.focusState.isFocused &&
                            _state.value.roomID.isBlank()
                )
            }


            is RoomSettingsEvent.UpdateRoomID -> {
                _state.value = roomState.value.copy(
                    roomID = roomState.value.inputRoomId
                )
                repository.updatePath(_state.value.inputRoomId)
            }

        }
    }

}