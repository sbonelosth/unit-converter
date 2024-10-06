package com.example.unitconverter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Don't make changes here

@Composable
fun ConversionScreen(
    title: String,
    items: List<String>,
    selectedConversion: String,
    onConversionSelected: (String) -> Unit,
    userInput: String,
    onUserInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    chatMessages: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    val appBg: Painter = painterResource(R.drawable.app_bg)
    var showAdditionalButtons by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(
            painter = appBg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { showAdditionalButtons = !showAdditionalButtons },
                    modifier = Modifier
                        .background(Color.Red, shape = CircleShape)
                        .width(260.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F8FF)),
                    shape = CircleShape
                ) {
                    Text(
                        text = selectedConversion,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                    )
                }

                if (showAdditionalButtons) {
                    items.forEach { item ->
                        if (item != selectedConversion) {
                            Button(
                                onClick = {
                                    onConversionSelected(item)
                                    showAdditionalButtons = false
                                },
                                modifier = Modifier
                                    .background(Color.Red, shape = CircleShape)
                                    .width(260.dp)
                                    .height(40.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F8FF)),
                                shape = CircleShape
                            ) {
                                Text(
                                    text = item,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                }
            }

            ConversionChat(chatMessages = chatMessages, modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(8.dp))

            InputRow(
                userInput = userInput,
                onUserInputChange = onUserInputChange,
                onSendClick = onSendClick,
                conversionType = selectedConversion
            )
        }
    }
}
