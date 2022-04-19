package com.mitm.android.grocerysharedlist.presentation.ui.items_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mitm.android.grocerysharedlist.ItemItem
import com.mitm.android.grocerysharedlist.R
import com.mitm.android.grocerysharedlist.model.Item
import com.mitm.android.grocerysharedlist.presentation.Screen
import com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable.InsertItemField
import kotlin.random.Random.Default.nextInt

@ExperimentalMaterialApi
@Composable
fun ItemsListScreen(navController: NavController, viewModel: ItemsListViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val inputItem = state.inputItem

    Scaffold(
        bottomBar = {
            BottomAppBar(
                // Defaults to null, that is, No cutout
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {
                IconButton(
                    onClick = {
                        viewModel.onEvent(ListEvent.UpdateListInRoom)
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

        Column() {
            InsertItemField(
                text = inputItem,
                onValueChange = {
                    viewModel.onEvent(ListEvent.EditInput(it))
                },
                onFocusChange = {
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp)
            )

            LazyColumn(
                modifier = Modifier.clickable {
                    viewModel.onEvent(ListEvent.DeleteAllItems)
                }
            ) {
                itemsIndexed(items = state.list) { index, item ->

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart
                                || it == DismissValue.DismissedToEnd
                            ) {
                                viewModel.onEvent(ListEvent.DeleteItem(item))
                            }
                            true
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {},
                        dismissContent = { ItemItem(item = item) },
                        directions = setOf(
                            DismissDirection.StartToEnd,
                            DismissDirection.EndToStart
                        )
                    )
                }
            }
        }
    }
}