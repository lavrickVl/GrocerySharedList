package com.mitm.android.grocerysharedlist.presentation.ui.room_settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.mitm.android.grocerysharedlist.R
import com.mitm.android.grocerysharedlist.core.Constants.ROOM_KEY
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.InsertItemField
import com.mitm.android.grocerysharedlist.presentation.ui.room_settings.composable.CardQR
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
    val scaffoldState = rememberScaffoldState()


    val barcodeEncoder = BarcodeEncoder()
    val bitmap = remember {
        mutableStateOf(
            barcodeEncoder.encodeBitmap(
                state.value.roomID,
                BarcodeFormat.QR_CODE,
                400,
                400
            )
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        )
        {
            val keyboardController = LocalSoftwareKeyboardController.current
            val msg = stringResource(id = R.string.room_new_msg)


            InsertItemField(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
                text = state.value.inputRoomId,
                hint = "RoomID",
                onValueChange = {
                    viewModel.onEvent(RoomSettingsEvent.EditInputRoomID(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }),
                isHintVisible = viewModel.roomIdContent.value.isHintVisible,
                onFocusChange = {
                    viewModel.onEvent(RoomSettingsEvent.ChangeContentFocus(it))
                },
                trailIcon = true,
                trailListener = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("This is info Snack")
                    }
                }
            )

            Button(
                onClick = {
                    viewModel.onEvent(
                        RoomSettingsEvent.UpdateRoomID
                    )

                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(msg)
                    }

                    keyboardController?.hide()
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        ROOM_KEY,
                        true
                    )
                    navController.popBackStack()
                },
            ) {}


            CardQR(
                size = 150.dp,
                bitmap = bitmap.value.asImageBitmap()
            )
        }
    }
}