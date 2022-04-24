package com.mitm.android.grocerysharedlist.presentation.ui.room_settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mitm.android.grocerysharedlist.core.Constants.ROOM_KEY
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.ListEvent
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.InsertItemField
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun RoomSettingsScreen(
    navController: NavController,
    viewModel: RoomSettingsViewModel = hiltViewModel()
) {

    val state = viewModel.roomState
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color.Magenta)
                .weight(1f,false)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val keyboardController = LocalSoftwareKeyboardController.current

                InsertItemField(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    text = state.value.inputRoomId,
                    onValueChange = {
                        viewModel.onEvent(RoomSettingsEvent.EditInputRoomID(it))
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5,
                    keyboardActions = KeyboardActions(
                        onGo = {
                            keyboardController?.hide()
                        }),
                    isHintVisible = viewModel.roomIdContent.value.isHintVisible,
                    onFocusChange = {
                        viewModel.onEvent(RoomSettingsEvent.ChangeContentFocus(it))
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.onEvent(
                            RoomSettingsEvent.UpdateRoomID
                        )

                        scope.launch {
                            snackbarHostState.showSnackbar("Saved new Room")

                            keyboardController?.hide()
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                ROOM_KEY,
                                true
                            )
                            navController.popBackStack()
                        }
                    },
                ) {}
            }
        }

        SnackbarHost(hostState = snackbarHostState, modifier = Modifier
            .background(Color.Magenta)
        )
    }
}