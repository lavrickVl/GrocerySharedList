package com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction

@ExperimentalComposeUiApi
@Composable
fun InsertItemField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "Input item",//"Enter your item...",
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    keyboardActions: KeyboardActions,
    trailIcon: Boolean = false,
    trailListener: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
//            .border(3.dp, MaterialTheme.colors.primary, shape = RoundedCornerShape(5.dp))
//            .padding(1.dp)
//            .border(4.dp, MaterialTheme.colors.secondary, shape = RoundedCornerShape(5.dp))

    ) {
        OutlinedTextField(
            label = { Text(hint)},
            value = text,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.primary,
                cursorColor = MaterialTheme.colors.primaryVariant
            ),
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,

            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = keyboardActions,
            trailingIcon = {
                if (trailListener != null) {
                    IconButton(onClick = trailListener) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "info"
                        )
                    }
                }
            }
        )
//        if (isHintVisible) {
//            Text(
//                text = hint,
//                style = TextStyle(fontSize = 16.sp),
//                color = Color.DarkGray,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
    }
}
