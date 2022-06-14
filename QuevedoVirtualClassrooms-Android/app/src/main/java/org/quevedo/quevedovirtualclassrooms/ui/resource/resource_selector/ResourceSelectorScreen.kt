package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.quevedo.quevedovirtualclassrooms.QueVirtualClassApp
import org.quevedo.quevedovirtualclassrooms.data.models.common.ResourceType
import org.quevedo.quevedovirtualclassrooms.ui.components.StandardCard

@Composable
fun ResourceSelectorScreen(
    classroomId: String,
    hasBackStack: Boolean,
    onBack: () -> Unit,
    onSelection: (classroomId: String, resourceType: ResourceType) -> Unit
) {
    QueVirtualClassApp(
        onBackAction = onBack,
        hasBackStack = hasBackStack
    ) { _ ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            items(ResourceType.values()) { item ->
                ResourceTypeCard(
                    resourceType = item,
                    selectResource = {
                        onSelection(classroomId, it)
                    })
            }
        }
    }
}

@Composable
fun ResourceTypeCard(
    resourceType: ResourceType,
    selectResource: (resourceType: ResourceType) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardColor: Color =
        when (resourceType) {
            ResourceType.VIDEO -> {
                MaterialTheme.colors.primary
            }
            ResourceType.IMAGE -> {
                MaterialTheme.colors.secondary
            }
            else -> {
                MaterialTheme.colors.surface
            }
        }
    val cardImage: ImageVector =
        when (resourceType) {
            ResourceType.VIDEO -> {
                Icons.Outlined.VideoLibrary
            }
            ResourceType.IMAGE -> {
                Icons.Outlined.Image
            }
            ResourceType.URL -> {
                Icons.Outlined.Link
            }
        }

    StandardCard(
        title = resourceType.stringValue,
        color = cardColor,
        cardAction = { selectResource(resourceType) },
        showArrow = true
    ) {
        Icon(
            cardImage,
            resourceType.stringValue,
            modifier = Modifier
                .size(50.dp),
            tint = cardColor
        )
    }
}