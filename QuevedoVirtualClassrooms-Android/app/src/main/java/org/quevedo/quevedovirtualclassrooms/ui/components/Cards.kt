package org.quevedo.quevedovirtualclassrooms.ui.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.quevedo.quevedovirtualclassrooms.ui.theme.QueVirtualClassTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StandardCard(
    modifier: Modifier = Modifier,
    title: String,
    secondaryText: String = "",
    color: Color,
    showArrow: Boolean = true,
    cardAction: (() -> Unit),
    passedIcon: @Composable (() -> Unit)
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .padding(5.dp)
            .fillMaxWidth(),
        border = BorderStroke(3.dp, Color.Black),
        shape = RoundedCornerShape(5.dp),
        backgroundColor = color,
        onClick = cardAction
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.wrapContentWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(
                        PaddingValues(start = 15.dp, bottom = 3.dp)
                    )
                )
                Text(
                    text = secondaryText,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(
                        PaddingValues(start = 25.dp, bottom = 3.dp)
                    )
                )
            }
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                passedIcon()
                if (showArrow) {
                    Icon(
                        Icons.Filled.NavigateNext,
                        "Arrow Right",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStandardCard() {
    QueVirtualClassTheme {
        StandardCard(
            title = "Imágenes",
            color = MaterialTheme.colors.surface,
            showArrow = true,
            cardAction = { Log.i(TAG, "PreviewStandardCard: ACTION!")}
        ) {
            Icon(Icons.Filled.Image, "Image", modifier = Modifier.size(50.dp))
        }
    }
}

@Preview
@Composable
fun PreviewVideoCard() {
    QueVirtualClassTheme {
        StandardCard(
            title = "Log4j2",
            secondaryText = "19-01-2022 23:59",
            color = MaterialTheme.colors.background,
            cardAction = { Log.i(TAG, "PreviewStandardCard: ACTION!")},
            showArrow = false
        ) {
            Icon(
                Icons.Outlined.PlayCircleFilled, "Image",
                modifier = Modifier
                    .size(50.dp),
                tint = MaterialTheme.colors.primaryVariant
            )
        }
    }
}