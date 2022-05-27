package org.quevedo.quevedovirtualclassrooms.ui.classroom.classroom_list

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.quevedo.quevedovirtualclassrooms.QueVirtualClassApp
import org.quevedo.quevedovirtualclassrooms.data.models.classroom.Classroom
import org.quevedo.quevedovirtualclassrooms.ui.components.MyTopBar
import org.quevedo.quevedovirtualclassrooms.ui.components.StandardCard

@Composable
fun ClassroomListScreen(
    hasBackStack: Boolean,
    onBack: () -> Unit,
    loggedUsername: String,
    onNavigate: (classroomId: String) -> Unit
) {
    val viewModel: ClassroomListViewModel = hiltViewModel()
    QueVirtualClassApp(
        hasBackStack = hasBackStack,
        onBackAction = onBack
    ) { paddingModifier ->
        val uiState = viewModel.uiState.collectAsState()
        val scaffoldState = rememberScaffoldState()
        remember {
            viewModel.handleEvent(ClassroomListContract.Event.SetUsername(username = loggedUsername))
            viewModel.handleEvent(ClassroomListContract.Event.GetObjects)
        }
        LaunchedEffect(key1 = true) {
            viewModel.uiState.collect { state ->
                state.error?.let {
                    val result = scaffoldState.snackbarHostState.showSnackbar(message = it)
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.handleEvent(ClassroomListContract.Event.ErrorMostrado)
                    }
                }
            }
        }
        Column(
            modifier = paddingModifier,
            verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Hi $loggedUsername \uD83D\uDC4B",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(start = 30.dp, top = 5.dp)
            )
            Text(
                text = "Ready to learn?",
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(start = 30.dp, top = 5.dp))
            ClassroomList(
                classrooms = uiState.value.classrooms ?: emptyList(),
                onClick = {
                    onNavigate(it.uuidClassroom)
                })
        }
    }
}

@Composable
fun ClassroomList(
    classrooms: List<Classroom>,
    onClick: (selectedClassroom: Classroom) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(classrooms) { classroom ->
            ClassroomItem(classroom = classroom, selectClassroom = onClick)
        }
    }
}

@Composable
fun ClassroomItem(
    classroom: Classroom,
    selectClassroom: (selectedClassroom: Classroom) -> Unit,
    modifier: Modifier = Modifier
) {
    StandardCard(
        title = classroom.name,
        secondaryText = classroom.course,
        color = MaterialTheme.colors.primaryVariant,
        cardAction = {
            selectClassroom(classroom)
        }) {}
}