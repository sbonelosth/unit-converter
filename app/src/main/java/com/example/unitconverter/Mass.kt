package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
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
fun MassScreen(modifier: Modifier = Modifier) {

}

fun handleMassSend() {

}
