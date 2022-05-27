package org.quevedo.quevedovirtualclassrooms

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.quevedo.quevedovirtualclassrooms.ui.components.MyTopBar
import org.quevedo.quevedovirtualclassrooms.ui.theme.QueVirtualClassTheme

@Composable
fun QueVirtualClassApp(
    onBackAction: () -> Unit,
    hasBackStack: Boolean,
    content: @Composable (modifier:Modifier) -> Unit
){
    QueVirtualClassTheme {
        Scaffold(
            topBar = {MyTopBar(
                onBackAction = onBackAction,
                hasBackStack = hasBackStack
            )}
        ) {
            content(Modifier.padding(it))
        }
    }
}