package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class Speed : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedScreen(Modifier, this)

        }
    }
}

@Composable
fun SpeedScreen(modifier: Modifier = Modifier) {
    var userInput by remember { mutableStateOf("") }
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var selectedConversion by remember { mutableStateOf("kilo To Miles") }
}

val items = listOf(
    "kiloPerHour to MilesPerHour",
    "milesPerHour to KiloPerHour"
)

 ConversionScreen (
  title = "Speed",
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
            }

            "kiloPerHour to MilesPerHour" -> {
                val result =UnitConverter().kilometersPerHourToMilesPerHour(userInput.toDouble())
                "What is $userInput in Kilometers per hour?" to "That is $result in miles per hour"
            }
            "milesPerHour to KiloPerHour" -> {
                var result = UnitConverter().milesPerHourToKilometersPerHour(userInput.toDouble())
                "What is $userInput in Miles per hour?" to "That is $result in Kilometres per hour"

            }
            updateState("", chatMessages + ("user" to userMessage) + ("response" to responseMessage))


        }

}
    @Preview(showBackground = true)
    @Composable
    fun SpeedPreview() {
        SpeedScreen(modifier = Modifier, context = null)
    }

