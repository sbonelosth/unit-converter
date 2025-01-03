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

class Fluids : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FluidsScreen(Modifier, this)
        }
    }
}

@Composable
fun FluidsScreen(modifier: Modifier = Modifier, context: Context?) {
    var userInput by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var selectedConversion by remember { mutableStateOf("Liters in Gallons") }

    val items = listOf(
        "Liters in Gallons",
        "Gallons in Liters",
        "Liters in Milliliters",
        "Milliliters in Liters"
    )

    ConversionScreen(
        title = "Fluids",
        items = items,
        selectedConversion = selectedConversion,
        onConversionSelected = { selectedConversion = it },
        userInput = userInput,
        onUserInputChange = { userInput = it },
        onSendClick = {
            handleFluidSend(userInput, chatMessages, selectedConversion) { newInput, newMessages ->
                userInput = newInput
                chatMessages = newMessages
            }
        },
        chatMessages = chatMessages,
        modifier = modifier,
        onBackClick = { context?.startActivity(Intent(context, MainActivity::class.java)) }
    )
}

fun handleFluidSend(
    userInput: String,
    chatMessages: List<Pair<String, String>>,
    conversionType: String,
    updateState: (String, List<Pair<String, String>>) -> Unit
) {
    val regex = Regex("^[0-9]*\\.?[0-9]+\$")

    if (userInput.isNotBlank() && regex.matches(userInput)) {
        val result = when (conversionType) {
            "Liters in Gallons" -> UnitConverter().litersToGallons(userInput.toDouble())
            "Gallons in Liters" -> UnitConverter().gallonsToLiters(userInput.toDouble())
            "Liters in Milliliters" -> UnitConverter().litersToMilliliters(userInput.toDouble())
            "Milliliters in Liters" -> UnitConverter().millilitersToLiters(userInput.toDouble())
            else -> "Invalid conversion type"
        }
        val userMessage = "What is $userInput $conversionType?"
        val responseMessage = "That is $result ${conversionType.substring(conversionType.indexOf("in") + 3)}"
        updateState("", chatMessages + ("user" to userMessage) + ("response" to responseMessage))
    } else {
        updateState("", chatMessages + ("user" to "What is $userInput $conversionType?") + ("response" to "Please enter a valid number."))
    }
}