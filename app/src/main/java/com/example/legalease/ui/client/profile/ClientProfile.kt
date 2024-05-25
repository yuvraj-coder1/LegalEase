package com.example.legalease.ui.client.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.legalease.R
import com.example.legalease.ui.viewModels.AuthViewModel
import com.example.ui.theme.Inter

@Composable
fun ClientProfile(modifier: Modifier = Modifier, viewModel: AuthViewModel, logOut: () -> Unit) {
    val client = viewModel.currentClient
    Column(
        modifier = modifier
            .padding(16.dp)
            .padding(top = 100.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.profile),
            fontSize = 32.sp,
            fontFamily = Inter,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(46.dp))
        Box {
            AsyncImage(
                model = null,
                error = painterResource(id = R.drawable.default_profile_image),
                contentDescription = null,
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
            )
        }


        Spacer(modifier = Modifier.height(18.dp))
        Column(
//            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Name",
                    modifier = Modifier.size(36.dp)
                )
//                Spacer(modifier = Modifier.width(5.dp))
                if (client != null) {
                    Text(
                        text = client.name,
                        fontSize = 18.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
            Row(

                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = "Email id",
                    modifier = Modifier.size(36.dp)
                )
//                Spacer(modifier = Modifier.width(10.dp))
                if (client != null) {
                    Text(
                        text = client.email,
                        fontSize = 18.sp,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(7.dp),
            onClick =logOut) {
            Text(
                text = stringResource(R.string.log_out),
                fontSize = 16.sp,
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}
