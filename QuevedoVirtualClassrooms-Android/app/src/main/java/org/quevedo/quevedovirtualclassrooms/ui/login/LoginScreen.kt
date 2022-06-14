package org.quevedo.quevedovirtualclassrooms.ui.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.quevedo.quevedovirtualclassrooms.data.models.user.LoginRequest

@Composable
fun LoginScreen(
    goToMainScreen: ((username: String) -> Unit)
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val mContext = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { state ->
            state.userHasLoggued.let {
                if (it) {
                    goToMainScreen(state.user)
                }
            }
            state.error?.let {
                Toast.makeText(mContext, it, Toast.LENGTH_SHORT).show()
                viewModel.handleEvent(LoginContract.Event.ErrorMostrado)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {

    }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.h2)

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Username") },
            value = uiState.value.user,
            onValueChange = {
                viewModel.handleEvent(LoginContract.Event.SetUserText(it))
            })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password") },
            value = uiState.value.password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewModel.handleEvent(LoginContract.Event.SetUserPass(it)) })

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    viewModel.handleEvent(
                        LoginContract.Event.DoLogin(
                            LoginRequest(uiState.value.user, uiState.value.password)
                        )
                    )
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}

@Preview
@Composable
fun PreviewLogin() {
    LoginScreen(goToMainScreen = {})
}