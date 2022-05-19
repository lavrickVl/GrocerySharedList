package com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import com.mitm.android.grocerysharedlist.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

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
    isError: Boolean = false,
    trailListener: (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            label = { Text(hint) },
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
        
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = isError,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -10.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Text(
                text = stringResource(id = R.string.error),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }


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
