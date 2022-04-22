package com.mitm.android.grocerysharedlist.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mitm.android.grocerysharedlist.presentation.theme.GrocerySharedListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GrocerySharedListTheme {
                Navigation()
            }
        }
    }
}


@Composable
fun InputItem(hint: String = "Enter text...") {
    var text by remember {
        mutableStateOf(hint)
    }

    OutlinedTextField(value = text, onValueChange = {
        text = it
    },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "email"
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "check"
                )
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InputItem()
}

