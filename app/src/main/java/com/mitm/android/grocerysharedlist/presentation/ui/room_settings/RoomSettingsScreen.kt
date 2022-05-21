package com.mitm.android.grocerysharedlist.presentation.ui.room_settings

import android.R.attr
import android.annotation.SuppressLint
import android.widget.ProgressBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
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
import com.mitm.android.grocerysharedlist.presentation.ui.dialogs.InfoDialog
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.InsertItemField
import com.mitm.android.grocerysharedlist.presentation.ui.room_settings.composable.CardQR
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@Composable
fun RoomSettingsScreen(
    navController: NavController,
    viewModel: RoomSettingsViewModel = hiltViewModel()
) {
    val state = viewModel.roomState
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState()

    val myClipboard = LocalContext.current.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

    var visible by remember { mutableStateOf(false) }

    scope.launch {
        //checking
        if (viewModel.checkRoomIsHome().await()) return@launch
        delay(600L)
        visible = true
    }


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

            val density = LocalDensity.current


            TopAppBar() {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }

                Spacer(Modifier.weight(1f, true))


                AnimatedVisibility(
                    visible = visible,
                    enter = slideInVertically {
                        // Slide in from 40 dp from the top.
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        // Expand from the top.
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        // Fade in with the initial alpha of 0.3f.
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    IconButton(onClick = {
                        viewModel.onEvent(RoomSettingsEvent.SetHomeRoomID)
                    }) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "home_room"
                        )
                    }
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
            //val title = stringResource(id = R.string.room_new_msg)

            val msg_err = stringResource(id = R.string.room_err_msg)
            val title_err = stringResource(id = R.string.error_title)

            val snck_msg = stringResource(id = R.string.snck_msg)

            val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
            val (showErrDialog, setShowErrDialog) = remember { mutableStateOf(false) }
            val isError = remember { mutableStateOf(false) }


            Column(
                modifier = Modifier.weight(1f, false),
            ) {
                val iconClearShow = remember {
                    mutableStateOf(false)
                }

                InsertItemField(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
                    text = state.value.inputRoomId,
                    hint = "RoomID",
                    onValueChange = {
                        viewModel.onEvent(RoomSettingsEvent.EditInputRoomID(it))
                        isError.value = false
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }),
                    isHintVisible = iconClearShow.value,//viewModel.roomIdContent.value.isHintVisible,
                    onFocusChange = {
                        iconClearShow.value = !it.isFocused
                        viewModel.onEvent(RoomSettingsEvent.ChangeContentFocus(it))
                    },
                    trailIcon = true,
                    trailListener = if (iconClearShow.value) {
                        {
                            setShowDialog(true)
                            viewModel.onEvent(RoomSettingsEvent.RoomIsNotExist)
                        }
                    } else {
                        {
                            viewModel.onEvent(RoomSettingsEvent.EditInputRoomID(""))
                            iconClearShow.value = true
                            isError.value = false
                        }
                    },

                    isError = isError.value
                )

//                    scope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar("This is info Snack")
//                    }

                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            keyboardController?.hide()

                            // Is room changed?
                            if (state.value.inputRoomId != state.value.roomID) {
                                viewModel.onEvent(
                                    RoomSettingsEvent.UpdateRoomID
                                )
                            } else isError.value = true

                        },
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }



            Column(
                modifier = Modifier.weight(1f, false),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var toast: Toast? = null

                CardQR(
                    size = 150.dp,
                    bitmap = bitmap.value.asImageBitmap(),
                    onClick = {
                        val myClip: ClipData = ClipData.newPlainText("buffer", state.value.roomID)
                        myClipboard.setPrimaryClip(myClip)

                        if (toast != null) toast?.cancel()
                        toast = Toast.makeText(
                            context,
                            "RoomId is copied: ${state.value.roomID}",
                            Toast.LENGTH_SHORT
                        )
                        toast?.show()
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (state.value.loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary
                    )
                } else {
                    if (state.value.navigatePopUp) {
                        if (state.value.roomIsExist) {
                            scope.launch {
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    ROOM_KEY,
                                    true
                                )
                                launch {
                                    delay(1500L)
                                    navController.popBackStack()
                                }

                                scaffoldState.snackbarHostState.showSnackbar(
                                    snck_msg,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        } else {
                            setShowErrDialog(true)
                            viewModel.onEvent(RoomSettingsEvent.RoomIsNotExist)
                        }
                    }
                }
            }


            InfoDialog(
                msg = msg,
                showDialog = showDialog,
                setShowDialog = setShowDialog
            )

            InfoDialog(
                title = title_err,
                msg = msg_err,
                isError = true,
                showDialog = showErrDialog,
                setShowDialog = setShowErrDialog
            )
        }
    }
}



