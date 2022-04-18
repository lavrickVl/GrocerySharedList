package com.mitm.android.grocerysharedlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Counter(count: Int = 0) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        val counter = remember {
            mutableStateOf(count)
        }

        IconButton(
            onClick = {
                counter.value -= 1
            },
//            modifier = Modifier.weight(2f, true)
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
            Text(text = counter.value.toString())
        }


        IconButton(
            onClick = {
                counter.value += 1
            },
//            modifier = Modifier.weight(2f, true)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "add"
            )
        }
    }


}


