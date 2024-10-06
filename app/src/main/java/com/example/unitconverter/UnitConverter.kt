package com.example.unitconverter

class UnitConverter {

    // Length conversions
    fun metersToKilometers(meters: Double): Double = meters / 1000
    fun kilometersToMeters(kilometers: Double): Double = kilometers * 1000
    fun feetToMeters(feet: Double): Double = feet * 0.3048
    fun metersToFeet(meters: Double): Double = meters / 0.3048

    // Temperature conversions
    fun celsiusToFahrenheit(celsius: Double): Double = (celsius * 9/5) + 32
    fun fahrenheitToCelsius(fahrenheit: Double): Double = (fahrenheit - 32) * 5/9
    fun celsiusToKelvin(celsius: Double) : Double = celsius + 273.15
    fun kelvinToCelsius(kelvin: Double) : Double = kelvin - 273.15

    // Fluid conversions
    fun litersToMilliliters(liters: Double): Double = liters * 1000
    fun millilitersToLiters(milliliters: Double): Double = milliliters / 1000
    fun gallonsToLiters(gallons: Double): Double = gallons * 3.78541
    fun litersToGallons(liters: Double): Double = liters / 3.78541

    // Speed conversions
    fun kilometersPerHourToMilesPerHour(kph: Double): Double = kph / 1.60934
    fun milesPerHourToKilometersPerHour(mph: Double): Double = mph * 1.60934

    // Mass conversions
    fun kilogramsToGrams(kilograms: Double): Double = kilograms * 1000
    fun gramsToKilograms(grams: Double): Double = grams / 1000
    fun poundsToKilograms(pounds: Double): Double = pounds * 0.453592
    fun kilogramsToPounds(kilograms: Double): Double = kilograms / 0.453592
}
