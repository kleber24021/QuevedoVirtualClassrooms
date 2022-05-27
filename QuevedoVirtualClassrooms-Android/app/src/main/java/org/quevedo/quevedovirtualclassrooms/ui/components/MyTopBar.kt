package org.quevedo.quevedovirtualclassrooms.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.quevedo.quevedovirtualclassrooms.ui.theme.QueVirtualClassTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyTopBar(
    onBackAction: () -> Unit,
    hasBackStack: Boolean,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxWidth(), color = MaterialTheme.colors.primary) {
        if (hasBackStack) {

        }
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onBackAction() }) {
                Surface(
                    modifier = modifier.size(50.dp),
                    color = Color.White.copy(alpha = 0f)
                ) {
                    Icon(Icons.Outlined.ArrowBack, "ArrowBack")
                }
            }
            Row(modifier = modifier.wrapContentSize()) {
                Surface(
                    modifier = modifier
                        .size(50.dp),
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape,
                    onClick = {/* TODO */ }
                ) {
                    Row(
                        modifier = modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "U", style = MaterialTheme.typography.h3)
                    }
                }
                Surface(
                    modifier = modifier.size(50.dp),
                    color = Color.White.copy(alpha = 0f)
                ) {
                    Icon(Icons.Outlined.Notifications, "Notifications")
                }
            }
        }
    }
}