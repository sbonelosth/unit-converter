package com.example.unitconverter

sealed class Routes(val route: String) {
    object Conversion : Routes("Conversion")
    object Response : Routes("Response")
}