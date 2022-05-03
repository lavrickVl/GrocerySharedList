package com.mitm.android.grocerysharedlist.presentation.ui.items_list.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mitm.android.grocerysharedlist.model.Item

@Composable
fun ItemItem(
    item: Item,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            val textColor = if (!isSystemInDarkTheme()) Color.Black else Color.White

            Text(
                text = item.count.toString(),
                color = textColor,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center,
            )

            Text(
                text = item.name,
                color = textColor,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(4f, true),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}