package com.example.unitconverter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class Speed : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedScreen(Modifier, this)
        }
    }
}

@Composable
fun SpeedScreen(modifier: Modifier = Modifier, context: Context?) {
    var userInput by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var selectedConversion by remember { mutableStateOf("km/h to mph") }

    val items = listOf(
        "km/h to mph",
        "mph to km/h"
    )

    ConversionScreen (
        title = "Speed",
        items = items,
        selectedConversion = selectedConversion,
        onConversionSelected = { selectedConversion = it },
        userInput = userInput,
        onUserInputChange = { userInput = it },
        onSendClick = {
            handleSpeedSend(userInput, chatMessages, selectedConversion) { newInput, newMessages ->
                userInput = newInput
                chatMessages = newMessages
            }
        },
        chatMessages = chatMessages,
        modifier = modifier,
        onBackClick = { context?.startActivity(Intent(context, MainActivity::class.java)) }
    )
}

fun handleSpeedSend(
    userInput: String,
    chatMessages: List<Pair<String, String>>,
    conversionType: String,
    updateState: (String, List<Pair<String, String>>) -> Unit
) {
    val regex = Regex("^[0-9]*\\.?[0-9]+\$")

    if (userInput.isNotBlank()) {
        val (userMessage, responseMessage) = if (regex.matches(userInput)) {
            when (conversionType) {

                "km/h to mph" -> {
                    val result =
                        UnitConverter().kilometersPerHourToMilesPerHour(userInput.toDouble())
                    "What is $userInput km/h in Miles Per Hour?" to "That is $result mph"

                }

                "mph to km/h" -> {
                    var result =
                        UnitConverter().milesPerHourToKilometersPerHour(userInput.toDouble())
                    "What is $userInput mph in Kilometers Per Hour?" to "That is $result in km/h"

                }

                else -> "Invalid conversion type" to "Invalid conversion type"
            }

        }
        else {
            "What is $userInput?" to "Invalid input type"
        }

        updateState(
            "",
            chatMessages + ("user" to userMessage) + ("response" to responseMessage)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpeedPreview()
{
    SpeedScreen(modifier = Modifier, context = null)
}
