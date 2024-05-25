package com.example.legalease.ui.lawyer.lawyerGetStartedScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.legalease.ui.components.ChipsComponent
import com.example.legalease.ui.signUp.SignUpScreenUiState
import com.example.legalease.ui.signUp.SignUpScreenViewModel
//import com.example.legalease.ui.LegalEaseTheme
import com.example.compose.LegalEaseTheme
import com.example.legalease.R

@Composable
fun LawyerGetStartedScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpScreenViewModel,
    navigateToSignIn: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = uiState.lawyerType,
            onValueChange = { viewModel.updateLawyerType(it) },
            label = { Text(stringResource(R.string.main_role)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.select_expertises), style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        ChipsComponent(
            skills = uiState.expertise,
            onChipClick = { viewModel.selectExpertise(it) },
            isThisIndexSelected = uiState.isThisExpertiseSelected,
            selectedColor = Color(207, 118, 54),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.aboutLawyer,
            onValueChange = { viewModel.updateAboutLawyer(it) },
            label = { Text(stringResource(R.string.about_lawyer)) },
            placeholder = { Text(stringResource(R.string.enter_about_lawyer), color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            minLines = 7,
            maxLines = 7
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.lawyerExperience,
            onValueChange = { viewModel.updateExperience(it) },
            label = { Text(stringResource(R.string.lawyer_experience)) },
            placeholder = { Text(stringResource(id = R.string.lawyer_experience), color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.lawyerLocation,
            onValueChange = { viewModel.updateLocation(it) },
            label = { Text(stringResource(id = R.string.lawyer_preferred_location)) },
            placeholder = { Text(stringResource(R.string.location), color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.fee,
            onValueChange = { viewModel.updateFee(it) },
            label = { Text(stringResource(R.string.enter_fee)) },
            placeholder = { Text(stringResource(R.string.enter_fee_in_rupees_per_hr), color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                viewModel.signUp()
                navigateToSignIn()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.End)
        ) {
            Text(text = stringResource(R.string.submit))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LawyerGetStartedScreenPreview(modifier: Modifier = Modifier) {
    val viewModel: SignUpScreenViewModel = hiltViewModel()
    LegalEaseTheme {
        LawyerGetStartedScreen(viewModel = viewModel)
    }
}