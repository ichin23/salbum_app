package com.ichin23.salbum.ui.screens.Login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.ichin23.salbum.ui.components.LogoApp
import com.ichin23.salbum.ui.theme.BluePrimary
import com.ichin23.salbum.ui.theme.GreenSecondary
import com.ichin23.salbum.ui.theme.WhiteText
import com.ichin23.salbum.R
import com.ichin23.salbum.core.utils.UiEvent
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.ui.theme.BackgroundLighterDark

@Composable
fun LoginScreen(
    onNavigate: (route: String)-> Unit,
    viewModel: LoginScreenVM = hiltViewModel<LoginScreenVM>(),
    modifier: Modifier = Modifier
){
    val loading  = viewModel.loading.collectAsState()

    LaunchedEffect(true) {
        viewModel.uiEvent.collect {event ->
            when(event){
                is UiEvent.Navigate -> onNavigate(event.route)
                UiEvent.PopBackStack -> TODO()
                is UiEvent.ShowSnackBar -> TODO()
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.fillMaxHeight(0.1f))
        LogoApp(WhiteText, imageHeight = 80.dp, fontSize = 40.sp, modifier = Modifier)
        Spacer(Modifier.fillMaxHeight(0.1f))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        32.dp,
                        32.dp,
                    )
                )
                .background(BluePrimary)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 30.dp)
            ) {
                Text("Faça seu login agora mesmo!", style = MaterialTheme.typography.titleMedium, color = WhiteText)
                TextField(
                    value = viewModel.emailState,
                    onValueChange = {it:String->viewModel.onEvent(LoginEvents.OnEmailChange(it))},
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
                    placeholder = { Text("Email", color = WhiteText) },
                    modifier = Modifier.border(BorderStroke(2.dp, WhiteText), RoundedCornerShape(12.dp))
                )
                TextField(
                    value = viewModel.passwordState,
                    onValueChange = {viewModel.onEvent(LoginEvents.OnPasswordChange(it))},
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent),
                    placeholder = { Text("Senha", color = WhiteText) },
                    visualTransformation = if (viewModel.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Image(
                            imageVector = ImageVector.vectorResource(if (viewModel.showPassword) R.drawable.eye_open else R.drawable.eye_closed),
                            contentDescription = "Icon eye",
                            colorFilter = ColorFilter.tint(WhiteText),
                            modifier = Modifier
                                .clickable {
                                    viewModel.onEvent(LoginEvents.OnShowPasswordClick)
                                }
                                .size(26.dp)
                        )
                    },
                    modifier = Modifier.border(BorderStroke(2.dp, WhiteText), RoundedCornerShape(12.dp)),

                )
                Button(
                    { viewModel.onSendLogin() },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WhiteText),
                    enabled = !loading.value,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(45.dp)
                ) {
                    if(loading.value){
                        CircularProgressIndicator()
                    }else{
                        Text("Login", color = BluePrimary)
                    }
                }

                TextButton({viewModel.onEvent(LoginEvents.OnSignupClick)}) {
                    Text("Criar Conta", color = GreenSecondary)
                }
            }
        }
    }
}


@Preview
@Composable
private fun LoginPreview() {
    LoginScreen({})
}