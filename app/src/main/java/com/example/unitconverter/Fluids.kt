package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

class Fluids : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FluidsScreen()
        }
    }
}

@Composable
fun FluidsScreen(modifier: Modifier = Modifier) {

}

fun handleFluidsSend() {

}
