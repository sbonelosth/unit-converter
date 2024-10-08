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

class Length : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LengthScreen(Modifier, this)
        }
    }
}

@Composable
fun LengthScreen(modifier: Modifier = Modifier, context : Context?) {
    var userInput by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var selectedConversion by remember { mutableStateOf("Meters to Kilometers") }

    val items = listOf(
        "Meters to Kilometers",
        "Kilometers to Meters",
        "Meters to Feet",
        "Feet to Meters"
    )

    ConversionScreen(
        title = "Length",
        items = items,
        selectedConversion = selectedConversion,
        onConversionSelected = { selectedConversion = it },
        userInput = userInput,
        onUserInputChange = { userInput = it },
        onSendClick = {
            handleLengthSend(userInput, chatMessages, selectedConversion) { newInput, newMessages ->
                userInput = newInput
                chatMessages = newMessages
            }
        },
        chatMessages = chatMessages,
        modifier = modifier,
        onBackClick = { context?.startActivity(Intent(context, MainActivity::class.java)) }
    )
}

fun handleLengthSend(
    userInput: String,
    chatMessages: List<Pair<String, String>>,
    conversionType: String,
    updateState: (String, List<Pair<String, String>>) -> Unit
) {
    val regex = Regex("^[0-9]*\\.?[0-9]+\$")

    if (userInput.isNotBlank()) {
        val (userMessage, responseMessage) = if (regex.matches(userInput)) {
            when (conversionType) {
                "Meters to Kilometers" -> {
                    val result =UnitConverter().metersToKilometers(userInput.toDouble())
                    "What is $userInput m in Kilometers?" to "That is $result km"
                }

                "Kilometers to Meters" -> {
                    val result = UnitConverter().kilometersToMeters(userInput.toDouble())
                    "What is $userInput km in Meters?" to "That is $result m"
                }

                "Feet to Meters" -> {
                    val result = UnitConverter().feetToMeters(userInput.toDouble())
                    "What is $userInput ft in Meters?" to "That is $result m"
                }
                "Meters to Feet" -> {
                    val result = UnitConverter().metersToFeet(userInput.toDouble())
                    "What is $userInput m in Feet?" to "That is $result ft"
                }

                else -> "Invalid conversion type" to "Invalid conversion type"
            }
        } else {
            "What is $userInput °C in Kelvin?" to "Invalid input type"
        }
        updateState("", chatMessages + ("user" to userMessage) + ("response" to responseMessage))
    }
}

@Preview(showBackground = true)
@Composable
fun LengthPreview() {
    LengthScreen(Modifier, null)
}
