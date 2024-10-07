package com.example.unitconverter

import android.annotation.SuppressLint

class UnitConverter {

    @SuppressLint("DefaultLocale")
    private fun roundToTwoDecimals(value: Double): Double {
        return String.format("%.2f", value).toDouble()
    }

    // Length conversions
    fun metersToKilometers(meters: Double): Double = roundToTwoDecimals(meters / 1000)
    fun kilometersToMeters(kilometers: Double): Double = roundToTwoDecimals(kilometers * 1000)
    fun feetToMeters(feet: Double): Double = roundToTwoDecimals(feet * 0.3048)
    fun metersToFeet(meters: Double): Double = roundToTwoDecimals(meters / 0.3048)

    // Temperature conversions
    fun celsiusToFahrenheit(celsius: Double): Double = roundToTwoDecimals((celsius * 9/5) + 32)
    fun fahrenheitToCelsius(fahrenheit: Double): Double = roundToTwoDecimals((fahrenheit - 32) * 5/9)
    fun celsiusToKelvin(celsius: Double): Double = roundToTwoDecimals(celsius + 273.15)
    fun kelvinToCelsius(kelvin: Double): Double = roundToTwoDecimals(kelvin - 273.15)

    // Fluid conversions
    fun litersToMilliliters(liters: Double): Double = roundToTwoDecimals(liters * 1000)
    fun millilitersToLiters(milliliters: Double): Double = roundToTwoDecimals(milliliters / 1000)
    fun gallonsToLiters(gallons: Double): Double = roundToTwoDecimals(gallons * 3.78541)
    fun litersToGallons(liters: Double): Double = roundToTwoDecimals(liters / 3.78541)

    // Speed conversions
    fun kilometersPerHourToMilesPerHour(kph: Double): Double = roundToTwoDecimals(kph / 1.60934)
    fun milesPerHourToKilometersPerHour(mph: Double): Double = roundToTwoDecimals(mph * 1.60934)

    // Mass conversions
    fun kilogramsToGrams(kilograms: Double): Double = roundToTwoDecimals(kilograms * 1000)
    fun gramsToKilograms(grams: Double): Double = roundToTwoDecimals(grams / 1000)
    fun poundsToKilograms(pounds: Double): Double = roundToTwoDecimals(pounds * 0.453592)
    fun kilogramsToPounds(kilograms: Double): Double = roundToTwoDecimals(kilograms / 0.453592)
}
