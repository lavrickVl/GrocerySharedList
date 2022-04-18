package com.mitm.android.grocerysharedlist.presentation.ui.items_list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mitm.android.grocerysharedlist.Counter
import com.mitm.android.grocerysharedlist.ItemItem
import com.mitm.android.grocerysharedlist.core.Constants
import com.mitm.android.grocerysharedlist.model.Item
import java.util.*

@ExperimentalMaterialApi
@Composable
fun ItemsListScreen(navController: NavController, viewModel: ItemsListViewModel = hiltViewModel()) {

//    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
                    Counter()
                }

                Spacer(modifier = Modifier.weight(1f, false))
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {  // Create a new user with a first, middle, and last name
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
        LazyColumn(
            modifier = Modifier.clickable {
                val sha = Date().hashCode()

                Log.d(Constants.TAG, "onCreate: $sha")
            }
        ) {
            itemsIndexed(items = listOf("123", "321","31","21")) { index, item ->

                val state = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart
                            || it == DismissValue.DismissedToEnd
                        ) {
//                            getData.removeAt(index)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = state,
                    background = {},
                    dismissContent = { ItemItem(item = Item(1, item) ) },
                    directions = setOf(
                        DismissDirection.StartToEnd,
                        DismissDirection.EndToStart
                    )
                )
            }
        }
    }
}