package com.mitm.android.grocerysharedlist.presentation.ui.room_settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mitm.android.grocerysharedlist.core.AppSettings
import com.mitm.android.grocerysharedlist.domain.repository.RepositoryGrocery
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.InputItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

            is RoomSettingsEvent.RoomIsNotExist-> {
                _state.value = roomState.value.copy(
                    roomIsExist = false,
                    navigatePopUp = false
                )
            }

            is RoomSettingsEvent.SetHomeRoomID-> {
                viewModelScope.launch {
                    val def = async { repository.setHomeRoomID() }
                    def.await()

                    _state.value = roomState.value.copy(
                        roomID = appSettings.getRoomID() ?: return@launch,
                        roomIsExist = true,
                        navigatePopUp = true
                    )

                    repository.updatePath(_state.value.roomID)
                }
            }


            is RoomSettingsEvent.UpdateRoomID -> {
                _state.value = roomState.value.copy(
                    loading = true,
                )

                if (roomState.value.inputRoomId.isEmpty()){
                    _state.value = roomState.value.copy(
                        roomIsExist = false,
                        navigatePopUp = false,
                        loading = false,
                    )
                    return
                }

                viewModelScope.launch {
                    //check is Room exceed
                    val isExceedDeferred = async {
                        repository.checkPathIsExists(roomState.value.inputRoomId)
                    }

                    val isExceed = isExceedDeferred.await()

                    if (isExceed) {
                        _state.value = roomState.value.copy(
                            roomID = roomState.value.inputRoomId,
                            roomIsExist = isExceed,
                            navigatePopUp = true
                        )

                        repository.updatePath(_state.value.inputRoomId)
                    } else {

                        _state.value = roomState.value.copy(
                            roomIsExist = isExceed,
                            navigatePopUp = true
                        )
                    }

                    delay(1000L)

                    _state.value = roomState.value.copy(
                        loading = false,
                    )
                }
            }

        }
    }
}