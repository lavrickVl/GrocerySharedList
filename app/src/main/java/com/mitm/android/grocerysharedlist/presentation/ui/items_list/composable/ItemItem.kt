package com.mitm.android.grocerysharedlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mitm.android.grocerysharedlist.model.Item

@Composable
fun ItemItem(
    item: Item
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = item.count.toString(),
            color = Color.Black,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            textAlign = TextAlign.Center,
            )

        Text(
            text = item.name,
            color = Color.Black,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(6f, true),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}