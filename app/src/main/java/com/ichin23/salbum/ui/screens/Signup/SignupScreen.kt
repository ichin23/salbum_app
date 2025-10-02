package com.ichin23.salbum.ui.screens.Signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.ui.theme.BluePrimary
import com.ichin23.salbum.ui.theme.LightGreyText
import com.ichin23.salbum.ui.theme.WhiteText

@Composable
fun SignupScreen(modifier: Modifier = Modifier, viewModel: SignupVM = hiltViewModel()) {
    var error = viewModel.error.collectAsStateWithLifecycle()
    var loading = viewModel.loading.collectAsStateWithLifecycle()
    var showPassword = viewModel.showPassword.collectAsStateWithLifecycle()

    Column(
        modifier.fillMaxSize().padding(20.dp, 50.dp)
    ) {
        Icon(Icons.Default.ArrowBack,
            contentDescription = "Botão voltar",
            tint = WhiteText,
            modifier = Modifier
                .clickable {
                }
                .size(30.dp)
        )
        Spacer(Modifier.height(30.dp))
        Text("Bem vindo(a) ao Salbum!", style = MaterialTheme.typography.displayMedium.copy(fontSize = 20.sp, lineHeight = 20.sp), color = WhiteText)
        Text("Vamos nos conectar", color = LightGreyText)
        Spacer(Modifier.height(50.dp))
        TextField(
            value = viewModel.nameState,
            onValueChange = {viewModel.onEvent(SignupEvents.OnNameChange(it))},
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
            placeholder = { Text("Nome", color = LightGreyText) },
            modifier = Modifier.fillMaxWidth().border(BorderStroke(2.dp, WhiteText), RoundedCornerShape(12.dp))
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = viewModel.usernameState,
            onValueChange = {viewModel.onEvent(SignupEvents.OnUsernameChange(it))},
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
            placeholder = { Text("Username", color = LightGreyText) },
            modifier = Modifier.fillMaxWidth().border(BorderStroke(2.dp, WhiteText), RoundedCornerShape(12.dp))
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = viewModel.emailState,
            onValueChange = {viewModel.onEvent(SignupEvents.OnEmailChange(it))},
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
            placeholder = { Text("Email", color = LightGreyText) },
            modifier = Modifier.fillMaxWidth().border(BorderStroke(2.dp, WhiteText), RoundedCornerShape(12.dp))
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            value = viewModel.passwordState,
            onValueChange = {it:String->viewModel.onEvent(SignupEvents.OnPasswordChange(it))},
            colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
            placeholder = { Text("Senha", color = LightGreyText) },
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Image(
                    imageVector = ImageVector.vectorResource(if (showPassword.value) com.ichin23.salbum.R.drawable.eye_open else com.ichin23.salbum.R.drawable.eye_closed),
                    contentDescription = "Icon eye",
                    colorFilter = ColorFilter.tint(WhiteText),
                    modifier = Modifier.clickable{
                        viewModel.onEvent(SignupEvents.OnShowPasswordClick)
                    }.size(26.dp)
                )
            },
            modifier = Modifier.fillMaxWidth().border(BorderStroke(2.dp, WhiteText), RoundedCornerShape(12.dp))
        )
        Spacer(Modifier.height(16.dp))
        error.value?.let {Text(it, color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))}
        Spacer(Modifier.height(16.dp))
        Button(
            {},
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),

            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(45.dp).
            align(Alignment.CenterHorizontally)
        ) {
            Text("Começar", color = WhiteText)
        }
    }
}

@Preview
@Composable
private fun SignupPreview() {
    SignupScreen()
}