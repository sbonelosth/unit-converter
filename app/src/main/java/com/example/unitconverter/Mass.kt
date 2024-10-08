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


class Mass : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MassScreen()
        }
    }
}

@Composable
fun MassScreen(modifier: Modifier = Modifier, context: Context? = null) {
    var userInput by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var selectedConversion by remember { mutableStateOf("Kilograms to Grams") }

    val items = listOf(
        "Kilograms to Grams",
        "Grams to Kilograms",
        "Pounds to Kilograms",
        "Kilograms to Pounds"
    )

    ConversionScreen(
        title = "Mass",
        items = items,
        selectedConversion = selectedConversion,
        onConversionSelected = { selectedConversion = it },
        userInput = userInput,
        onUserInputChange = { userInput = it },
        onSendClick = {
            handleMassSend(userInput, chatMessages, selectedConversion) { newInput, newMessages ->
                userInput = newInput
                chatMessages = newMessages
            }
        },
        chatMessages = chatMessages,
        modifier = modifier,
        onBackClick = { context?.startActivity(Intent(context, MainActivity::class.java)) }
    )
}

fun handleMassSend(
    userInput: String,
    chatMessages: List<Pair<String, String>>,
    conversionType: String,
    updateState: (String, List<Pair<String, String>>) -> Unit
) {
    val regex = Regex("^[0-9]*\\.?[0-9]+\$")

    if (userInput.isNotBlank() && regex.matches(userInput)) {
        val result = when (conversionType) {
            "Kilograms to Grams" -> UnitConverter().kilogramsToGrams(userInput.toDouble())
            "Grams to Kilograms" -> UnitConverter().gramsToKilograms(userInput.toDouble())
            "Pounds to Kilograms" -> UnitConverter().poundsToKilograms(userInput.toDouble())
            "Kilograms to Pounds" -> UnitConverter().kilogramsToPounds(userInput.toDouble())
            else -> "Invalid conversion type"
        }
        val userMessage = "What is $userInput $conversionType?"
        val responseMessage = "That is $result ${conversionType.substring(conversionType.indexOf("to") + 3)}"
        updateState("", chatMessages + ("user" to userMessage) + ("response" to responseMessage))
    } else {
        updateState("", chatMessages + ("user" to "What is $userInput $conversionType?") + ("response" to "Please enter a valid number."))
    }
}

