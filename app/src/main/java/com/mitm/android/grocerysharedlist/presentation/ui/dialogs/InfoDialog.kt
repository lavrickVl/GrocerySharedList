package com.mitm.android.grocerysharedlist.presentation.ui.dialogs

import androidx.compose.foundation.border
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InfoDialog(
    title: String = "Info",
    msg: String = "This is a text on the dialog",
    isError: Boolean = false,
    showDialog: Boolean = true,
    setShowDialog: (Boolean) -> Unit
){
    if (showDialog) {
        AlertDialog(
            modifier = if (isError) Modifier.border(2.dp, color = Color.Red) else Modifier,
            onDismissRequest = {
            },
            title = {
                Text(title)
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                    },
                ) {
                    Text("Ok")
                }
            },
//            dismissButton = {
//                Button(
//                    onClick = {
//                        // Change the state to close the dialog
//                        setShowDialog(false)
//                    },
//                ) {
//                    Text("Dismiss")
//                }
//            },
            text = {
                Text(msg)
            },
        )
    }
}