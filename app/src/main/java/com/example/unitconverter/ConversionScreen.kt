package com.example.unitconverter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
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
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val appBg: Painter = painterResource(R.drawable.app_bg)
    var showAdditionalButtons by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Image(
            painter = appBg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
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
                fontFamily = montserattBold,
                modifier = Modifier.padding(16.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Click to Change Conversion Type", color = Color(0xfff0f8ff))
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

            ConversionChat(chatMessages, selectedConversion, Modifier.weight(1f))

            val icons = listOf(
                R.drawable.ruler,
                R.drawable.thermometer,
                R.drawable.water,
                R.drawable.speed,
                R.drawable.scale
            )

            val selectedIcon = when (title) {
                "Length" -> icons[0]
                "Temperature" -> icons[1]
                "Fluids" -> icons[2]
                "Speed" -> icons[3]
                "Mass" -> icons[4]
                else -> icons[0]
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = selectedIcon),
                    contentDescription = null,
                    modifier = Modifier.width(40.dp)
                )

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .background(Color.Transparent)
                        .clickable { onBackClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.left_arrow),
                        contentDescription = null,
                        tint = Color(0xffff5757)
                    )
                }
            }

            InputRow(
                userInput = userInput,
                onUserInputChange = onUserInputChange,
                onSendClick = onSendClick,
                conversionType = selectedConversion
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview (showBackground = true)
@Composable
fun ConversionScreenPreview() {
    var userInput by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var selectedConversion by remember { mutableStateOf("Celsius in Fahrenheit") }

    val items = listOf(
        "Celsius in Fahrenheit",
        "Fahrenheit in Celsius",
        "Celsius in Kelvin",
        "Kelvin in Celsius"
    )

    ConversionScreen(
        title = "Temperature",
        items = items,
        selectedConversion = selectedConversion,
        onConversionSelected = { selectedConversion = it },
        userInput = userInput,
        onUserInputChange = { userInput = it },
        onSendClick = {
            handleTempSend(userInput, chatMessages, selectedConversion) { newInput, newMessages ->
                userInput = newInput
                chatMessages = newMessages
            }
        },
        chatMessages = chatMessages,
        modifier = Modifier,
        onBackClick = { }
    )
}
