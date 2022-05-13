package org.quevedo.quevedovirtualclassrooms

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import org.quevedo.quevedovirtualclassrooms.ui.theme.QueVirtualClassTheme

@Composable
fun QueVirtualClassApp(content: @Composable () -> Unit){
    QueVirtualClassTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}