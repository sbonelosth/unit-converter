package com.example.unitconverter

import java.math.BigDecimal
import java.math.RoundingMode

class UnitConverter {

    // Helper function to format the result to 2 decimal places
    private fun formatResult(value: Double): Double {
        return BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    // Length conversions
    fun metersToKilometers(meters: Double): Double = formatResult(meters / 1000)
    fun kilometersToMeters(kilometers: Double): Double = formatResult(kilometers * 1000)
    fun feetToMeters(feet: Double): Double = formatResult(feet * 0.3048)
    fun metersToFeet(meters: Double): Double = formatResult(meters / 0.3048)

    // Temperature conversions
    fun celsiusToFahrenheit(celsius: Double): Double = formatResult((celsius * 9/5) + 32)
    fun fahrenheitToCelsius(fahrenheit: Double): Double = formatResult((fahrenheit - 32) * 5/9)
    fun celsiusToKelvin(celsius: Double): Double = formatResult(celsius + 273.15)
    fun kelvinToCelsius(kelvin: Double): Double = formatResult(kelvin - 273.15)

    // Fluid conversions
    fun litersToMilliliters(liters: Double): Double = formatResult(liters * 1000)
    fun millilitersToLiters(milliliters: Double): Double = formatResult(milliliters / 1000)
    fun gallonsToLiters(gallons: Double): Double = formatResult(gallons * 3.785411784)
    fun litersToGallons(liters: Double): Double = formatResult(liters / 3.785411784)

    // Speed conversions
    fun kilometersPerHourToMilesPerHour(kph: Double): Double = formatResult(kph / 1.60934)
    fun milesPerHourToKilometersPerHour(mph: Double): Double = formatResult(mph * 1.60934)

    // Mass conversions
    fun kilogramsToGrams(kilograms: Double): Double = formatResult(kilograms * 1000)
    fun gramsToKilograms(grams: Double): Double = formatResult(grams / 1000)
    fun poundsToKilograms(pounds: Double): Double = formatResult(pounds * 0.453592)
    fun kilogramsToPounds(kilograms: Double): Double = formatResult(kilograms / 0.453592)
}
