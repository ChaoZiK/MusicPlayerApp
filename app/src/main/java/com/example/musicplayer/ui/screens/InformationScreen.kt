package com.example.musicplayer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.ui.components.buttons.BackButton

@Composable
fun InformationScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(
                    onBackClick = onBackClick,
                    modifier = Modifier.size(40.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Information",
                    style = MaterialTheme.typography.headlineMedium
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Trần Thế Phong - ITCSIU21215",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Lê Thành Vinh - ITCSIU21246",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Phan Ngọc Tuấn - ITITIU20344",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Lê Minh Phượng - ITITIU20281",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "From Pocket Programmers Team",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Text(
                    text = "VIETNAM INTERNATIONAL UNIVERSITY\n" +
                            "HO CHI MINH CITY\n" +
                            "INTERNATIONAL UNIVERSITY",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "University Logo",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(top = 20.dp)
                )
            }
        }
    }
}