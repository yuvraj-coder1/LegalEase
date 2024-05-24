package com.example.legalease.ui.signIn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.legalease.R
import com.example.legalease.ui.components.CommonProgressBar


@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignUpClicked: () -> Unit = {},
    signInViewModel: SignInScreenViewModel = viewModel(),
    navigateToHome: () -> Unit = {}
) {
    val context = LocalContext.current
    val signInUiState by signInViewModel.uiState.collectAsState()
    val inprogress = signInViewModel.inProcess
    LaunchedEffect(key1 = signInViewModel.isSignedIn) {
        if (signInViewModel.isSignedIn) {
            navigateToHome()
        }
    }
    SignInScreenContent(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        username = signInUiState.username,
        password = signInUiState.password,
        isLawyer = signInUiState.isLawyer,
        onSignIn = {
            if(signInUiState.username.isEmpty() && signInUiState.password.isNotEmpty())
                Toast.makeText(context, "Username can't be empty!", Toast.LENGTH_SHORT).show()
            else if(signInUiState.password.isEmpty() && signInUiState.username.isNotEmpty())
                Toast.makeText(context, "Password can't be empty!", Toast.LENGTH_SHORT).show()
            else if(signInUiState.username.isEmpty() && signInUiState.password.isEmpty())
                Toast.makeText(context, "Enter your credentials!", Toast.LENGTH_SHORT).show()
            else
            signInViewModel.signIn()
        },
        onUsernameChange = { signInViewModel.updateUsername(it) },
        onPasswordChange = { signInViewModel.updatePassword(it) },
        onIsLawyerChange = { signInViewModel.updateIsLawyer(it) },
        onSignUpClicked = onSignUpClicked,
        navigateToHome = navigateToHome
    )
    if (inprogress)
        CommonProgressBar()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreenTopBar(modifier: Modifier = Modifier, onClick: () -> Unit, showIcon: Boolean) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier,
//                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LegalEase",
                    style = TextStyle(
                        color = Color(red = 52, green = 86, blue = 150),
                        fontSize = 34.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(x = 0f, y = 1f)
                        ),
                    ),
                )
                if(showIcon) {
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {onClick()}) {
                        Icon(
                            painter = painterResource(id = R.drawable.chatbot_icon),
                            contentDescription = "ChatBot",
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun SignInScreenContent(
    modifier: Modifier = Modifier,
    username: String,
    password: String,
    isLawyer: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignIn: () -> Unit = {},
    onIsLawyerChange: (Boolean) -> Unit = {},
    onSignUpClicked: () -> Unit,
    navigateToHome: () -> Unit
) {
    var hidePassWord by rememberSaveable {
        mutableStateOf(true)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Text(text = "Log in", style = TextStyle(fontSize = 30.sp), fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.size(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Button(
                onClick = { onIsLawyerChange(true) },
                colors = if (isLawyer) ButtonDefaults.buttonColors(Color.Black) else ButtonDefaults.buttonColors(
                    Color.LightGray
                ),
//                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Lawyer", color = if (isLawyer) Color.White else Color.Black)
                if(isLawyer) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.lawyer_icon),
                        contentDescription = "lawyer image",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Button(
                onClick = { onIsLawyerChange(false) },
                colors = if (isLawyer) ButtonDefaults.buttonColors(Color.LightGray) else ButtonDefaults.buttonColors(
                    Color.Black
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Client", color = if (!isLawyer) Color.White else Color.Black)
                if(!isLawyer) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.client_icon),
                        contentDescription = "client image",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(30.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { onUsernameChange(it) },
            label = { Text(text = "Username") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Username"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Key,
                    contentDescription = "Password"
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (hidePassWord) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                Icon(
                    imageVector = if (!hidePassWord) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Hide Password",
                    modifier = Modifier.clickable { hidePassWord = !hidePassWord })
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onSignIn,
            colors = ButtonDefaults.buttonColors(
                Color.Black
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Log in", fontSize = 16.sp, modifier = Modifier.padding(4.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                Color.Black
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Log in with Google",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Don't have an account?",
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onSignUpClicked,
            colors = ButtonDefaults.buttonColors(
                Color.Black
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Sign Up", fontSize = 16.sp, modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignInScreenPreview(modifier: Modifier = Modifier) {
    SignInScreen()
}