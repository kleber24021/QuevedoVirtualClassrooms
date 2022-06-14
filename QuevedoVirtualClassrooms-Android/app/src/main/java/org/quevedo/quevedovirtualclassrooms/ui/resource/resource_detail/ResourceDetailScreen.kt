package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.quevedo.quevedovirtualclassrooms.QueVirtualClassApp
import org.quevedo.quevedovirtualclassrooms.data.models.common.ResourceType.*
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceCommentDTO
import org.quevedo.quevedovirtualclassrooms.ui.components.StandardCard
import org.quevedo.quevedovirtualclassrooms.ui.login.LoginContract

@Composable
fun ResourceDetailScreen(
    goToWatcher: (resourceId: String) -> Unit,
    hasBackStack: Boolean,
    onBack: () -> Unit,
    resourceId: String
) {
    val viewModel: ResourceDetailViewModel = hiltViewModel()
    QueVirtualClassApp(
        hasBackStack = hasBackStack,
        onBackAction = onBack
    ) { _ ->
        val uiState = viewModel.uiState.collectAsState()
        val mContext = LocalContext.current

        remember {
            viewModel.handleEvent(ResourceDetailContract.Event.SetResourceId(resourceId = resourceId))
            viewModel.handleEvent(ResourceDetailContract.Event.GetObject)
        }
        LaunchedEffect(key1 = true) {
            viewModel.uiState.collect { state ->
                state.error?.let {
                    Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
                    viewModel.handleEvent(ResourceDetailContract.Event.ErrorMostrado)
                }
            }
        }
        val resource = uiState.value.resource
        var showCommentEditor by remember {
            mutableStateOf(false)
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Column (modifier = Modifier.fillMaxSize()){
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { goToWatcher(resourceId) }) {
                        when (resource?.resourceType) {
                            VIDEO -> Text(text = "Ver vídeo")
                            IMAGE -> Text(text = "Ver imagen")
                            URL -> Text(text = "Ir al enlace")
                            else -> Text(text = "Ver recurso")
                        }
                    }
                    Button(onClick = {showCommentEditor = !showCommentEditor}) {
                        Text(text = "Añadir comentario")
                    }
                }
                if (showCommentEditor){
                    CommentEditor({ comment ->
                        viewModel.handleEvent(ResourceDetailContract.Event.SendComment(comment, null))
                        showCommentEditor = false
                    })
                }
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (resource != null) {
                        items(uiState.value.comments){resource ->
                            CommentItem(comment = resource)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CommentItem(
    comment: ResourceCommentDTO
) {
    StandardCard(
        title = comment.usernameOwner,
        color = MaterialTheme.colors.background,
        cardAction = {},
        secondaryText = comment.text,
        showArrow = false
    ){}
}

@Composable
fun CommentEditor(
    sendComment: (comment:String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
){
    var commentText by remember {
        mutableStateOf("")
    }
    TextField(
        value = commentText,
        onValueChange = {commentText = it},
        placeholder = { Text(text = "Escribe tu comentario aquí...") },
        singleLine = false,
        maxLines = 4,
        modifier = modifier
    )
    TextButton(
        onClick = {
            sendComment(commentText)
            commentText = ""
        },
    ) {
        Text(text = "Enviar")
    }
}
