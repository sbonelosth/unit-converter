package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

class Temperature : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureScreen()
        }
    }
}

@Composable
fun TemperatureScreen(modifier: Modifier = Modifier) {
    var userInput by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var selectedConversion by remember { mutableStateOf("Celsius to Fahrenheit") }

    val items = listOf(
        "Celsius to Fahrenheit",
        "Fahrenheit to Celsius",
        "Celsius to Kelvin",
        "Kelvin to Celsius"
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
        modifier = modifier
    )
}

fun handleTempSend(
    userInput: String,
    chatMessages: List<Pair<String, String>>,
    conversionType: String,
    updateState: (String, List<Pair<String, String>>) -> Unit
) {
    val regex = Regex("^[0-9]*\\.?[0-9]+\$")

    if (userInput.isNotBlank()) {
        val (userMessage, responseMessage) = if (regex.matches(userInput)) {
            when (conversionType) {
                "Celsius to Fahrenheit" -> {
                    val result = UnitConverter().celsiusToFahrenheit(userInput.toDouble())
                    "What is $userInput °C in Fahrenheit?" to "That is $result °F"
                }
                "Fahrenheit to Celsius" -> {
                    val result = UnitConverter().fahrenheitToCelsius (userInput.toDouble())
                    "What is $userInput °F in Celsius?" to "That is $result °C"
                }
                "Celsius to Kelvin" -> {
                    val result = UnitConverter().celsiusToKelvin(userInput.toDouble())
                    "What is $userInput °C in Kelvin?" to "That is $result K"
                }
                "Kelvin to Celsius" -> {
                    val result = UnitConverter().kelvinToCelsius(userInput.toDouble())
                    "What is $userInput K in Celsius?" to "That is $result °C"
                }
                else -> "Invalid conversion type" to "Invalid conversion type"
            }
        } else {
            "What is $userInput °C in Kelvin?" to "Invalid input type"
        }
        updateState("", chatMessages + ("user" to userMessage) + ("response" to responseMessage))
    }
}
