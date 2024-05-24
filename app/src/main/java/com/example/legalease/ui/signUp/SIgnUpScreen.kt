  package com.example.legalease.ui.signUp

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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.legalease.R
import com.example.legalease.ui.navigation.LawyerGetStartedScreen
import com.example.legalease.ui.signIn.SignInScreen
import com.example.legalease.ui.signIn.SignInScreenContent
import com.example.legalease.ui.signIn.SignInScreenTopBar
import com.example.legalease.ui.signIn.SignInScreenViewModel

  @Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onSignInClicked: () -> Unit = {},
    signUpViewModel: SignUpScreenViewModel = viewModel(),
    navController: NavController
) {

    val signUpUiState by signUpViewModel.uiState.collectAsState()
    val context = LocalContext.current

    SignUpScreenContent(
        modifier = Modifier
            .padding(horizontal = 24.dp),
//        navigateToLawyerGetStartedScreen = navigateToLawyerGetStartedScreen,
        navigateToLawyerGetStartedScreen = {
            if(signUpUiState.username.isEmpty() ||
                signUpUiState.password.isEmpty() ||
                signUpUiState.confirmPassword.isEmpty() ||
                signUpUiState.email.isEmpty() ||
                signUpUiState.governmentId.isEmpty()
            )
                Toast.makeText(context, "Enter all fields!", Toast.LENGTH_SHORT).show()
            else
                navController.navigate(LawyerGetStartedScreen)
        },
        username = signUpUiState.username,
        password = signUpUiState.password,
        isLawyer = signUpUiState.isLawyer,
        onUsernameChange = { signUpViewModel.updateUsername(it) },
        onPasswordChange = { signUpViewModel.updatePassword(it) },
        onIsLawyerChange = { signUpViewModel.updateIsLawyer(it) },
        confirmPassword = signUpUiState.confirmPassword,
        onConfirmPasswordChange = { signUpViewModel.updateConfirmPassword(it) },
        governmentId = signUpUiState.governmentId,
        onGovernmentIdChange = { signUpViewModel.updateGovernmentId(it) },
        email = signUpUiState.email,
        onEmailChange = { signUpViewModel.updateEmail(it) },
        onSignUpClick = {
            if(signUpUiState.username.isEmpty() ||
                signUpUiState.password.isEmpty() ||
                signUpUiState.confirmPassword.isEmpty() ||
                signUpUiState.email.isEmpty()
            )
                Toast.makeText(context, "Enter all fields!", Toast.LENGTH_SHORT).show()
            else
            signUpViewModel.signUp()
        },
        onSignInClicked = onSignInClicked
    )

}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    navigateToLawyerGetStartedScreen: () -> Unit = {},
    username: String,
    password: String,
    isLawyer: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onIsLawyerChange: (Boolean) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    governmentId: String,
    onGovernmentIdChange: (String) -> Unit,
    onSignUpClick: () -> Unit = {},
    email: String,
    onEmailChange: (String) -> Unit = {},
    onSignInClicked: () -> Unit = {}
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
        Text(text = "Sign Up", style = TextStyle(fontSize = 30.sp), fontWeight = FontWeight.Black)
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
        Spacer(modifier = Modifier.size(24.dp))
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
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { onEmailChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(text = "Email") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Email"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        if (isLawyer) {
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                value = governmentId,
                onValueChange = { onGovernmentIdChange(it) },
                label = { Text(text = "Government ID") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.CreditCard,
                        contentDescription = "Government ID"
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(text = "Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Key,
                    contentDescription = "Password"
                )
            },
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
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { onConfirmPasswordChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            label = { Text(text = "Confirm Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Key,
                    contentDescription = "ConfirmPassword"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = if(isLawyer) navigateToLawyerGetStartedScreen else onSignUpClick,
            colors = ButtonDefaults.buttonColors(
                Color.Black
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Sign Up", fontSize = 16.sp, modifier = Modifier.padding(4.dp))
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
                text = "Sign Up with Google",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Already have an account?",
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onSignInClicked,
            colors = ButtonDefaults.buttonColors(
                Color.Black
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Log in", fontSize = 16.sp, modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SignUpScreenPreview(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    SignUpScreen(onSignInClicked = {}, navController = navController)
}