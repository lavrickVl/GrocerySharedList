package com.mitm.android.grocerysharedlist.presentation.ui.items_list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.ItemItem
import com.mitm.android.grocerysharedlist.R
import com.mitm.android.grocerysharedlist.core.Constants.PREF_ROOM_KEY
import com.mitm.android.grocerysharedlist.core.Constants.ROOM_KEY
import com.mitm.android.grocerysharedlist.presentation.Screen
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.InsertItemField
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ItemsListScreen(navController: NavController, viewModel: ItemsListViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    lateinit var listState: LazyListState


    val inputItem = state.inputItem

    val roomIsChanged = navController.currentBackStackEntry?.savedStateHandle?.get<Boolean>(ROOM_KEY)
    if (roomIsChanged == true) {
        navController.currentBackStackEntry?.savedStateHandle?.set(ROOM_KEY, false)
        Toast.makeText(navController.context, "" + roomIsChanged, Toast.LENGTH_SHORT).show()

        viewModel.onEvent(ListEvent.UpdateRoom)
    }

    Scaffold(
        topBar = {
            TopAppBar {
                Spacer(Modifier.width(8.dp))
                Text(state.roomID, fontSize = 22.sp)
                Spacer(Modifier.weight(1f, true))
                IconButton(onClick = {
                    navController.navigate(Screen.RoomSettingsScreen.route)
                }) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "settings"
                    )
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                // Defaults to null, that is, No cutout
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(ListEvent.DeleteAllItems)
                    },
                    modifier = Modifier.weight(1f, false)
                ) {
                    Icon(
                        Icons.Filled.Delete, contentDescription = "clear all"
                    )
                }


                Box(
                    modifier = Modifier
                        .weight(5f)
                        .fillMaxWidth()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.counterMinus()
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_remove_24),
                                contentDescription = "remove"
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.Red),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = state.counter.toString())
                        }


                        IconButton(
                            onClick = {
                                viewModel.counterPlus()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "add"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f, false))
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(ListEvent.InsertItem)
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
//                    navController.navigate(
//                        Screen.RoomSettingsScreen.route
//                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "add",
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        isFloatingActionButtonDocked = true,
    ) {

        val keyboardController = LocalSoftwareKeyboardController.current
        listState = rememberLazyListState()

        Column() {
            InsertItemField(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp, end = 12.dp),
                text = inputItem,
                onValueChange = {
                    viewModel.onEvent(ListEvent.EditInput(it))
                },
                onFocusChange = {
                    viewModel.onEvent(ListEvent.ChangeContentFocus(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
                isHintVisible = viewModel.itemContent.value.isHintVisible,
                keyboardActions = KeyboardActions(
                    onGo = {
                        viewModel.onEvent(ListEvent.InsertItem)
                        keyboardController?.hide()

                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                    })
            )


            LazyColumn(
                state = listState,
                modifier = Modifier
//                    .clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() }
//                    ) {}
                    .padding(bottom = it.calculateBottomPadding()),
            ) {
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(18.dp)
                    )
                }


                itemsIndexed(
                    items = state.list,
                    key = { index, item ->
                        item.hashCode()
                    }
                ) { index, item ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart
//                                || it == DismissValue.DismissedToEnd
                            ) {
                                Toast.makeText(
                                    navController.context,
                                    "indexOf: $index",
                                    Toast.LENGTH_SHORT
                                ).show()
                                viewModel.onEvent(ListEvent.DeleteItem(item))
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        modifier = Modifier.animateItemPlacement(),
                        background = {
                            val color = when (dismissState.dismissDirection) {
                                DismissDirection.StartToEnd -> Color.Transparent
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        dismissContent = {
                            ItemItem(
                                item = item,
                            )
                        },
                        directions = setOf(
//                            DismissDirection.StartToEnd,
                            DismissDirection.EndToStart
                        )
                    )
                }
            }
        }
    }
}