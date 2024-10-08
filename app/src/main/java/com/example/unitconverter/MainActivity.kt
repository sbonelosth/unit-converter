package com.example.unitconverter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                LandingPage(
                    context = this
                )
            }
        }
    }
}

val montseratt = FontFamily(Font(R.font.montserrat_alternates_regular))
val montserattBold = FontFamily(Font(R.font.montserrat_alt_bold))
val typographica = FontFamily(Font(R.font.typographica))

@Composable
fun LandingPage(context: Context?) {
    val appBg: Painter = painterResource(R.drawable.app_bg)

    Box(modifier = Modifier) {
        Image(
            painter = appBg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            DateTimeWidget()

            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontFamily = montserattBold,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
            val items = listOf("Length", "Temperature", "Fluids", "Speed", "Mass")
            val icons = listOf(
                R.drawable.ruler,
                R.drawable.thermometer,
                R.drawable.water,
                R.drawable.speed,
                R.drawable.scale
            )
            val painters: List<Painter> = icons.map { painterResource(id = it) }
            ItemList(items = items, painters = painters) { item ->
                try {
                    when (item) {
                        "Length" -> context?.startActivity(Intent(context, Length::class.java))
                        "Temperature" -> context?.startActivity(Intent(context, Temperature::class.java))
                        "Fluids" -> context?.startActivity(Intent(context, Fluids::class.java))
                        "Speed" -> context?.startActivity(Intent(context, Speed::class.java))
                        "Mass" -> context?.startActivity(Intent(context, Mass::class.java))
                    }
                } catch (e: Exception) {
                    Log.e("LandingPage", "Error starting activity for $item", e)
                }
            }

            Spacer(modifier = Modifier.height(80.dp))

            Box(
                modifier = Modifier
                    .width(40.dp)
                    .background(Color.Transparent)
                    .clickable { (context as? Activity)?.finishAffinity() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cross),
                    contentDescription = null,
                    tint = Color(0xffff5757)
                )
            }
        }
    }
}


@Composable
fun ItemRow(text: String, painter: Painter, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp, horizontal = 14.dp)
            .height(80.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xff0097b2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text[0].toString(),
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = typographica,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = montserattBold,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .width(40.dp)
                .aspectRatio(1f)
        )
    }
}

@Composable
fun ItemList(items: List<String>, painters: List<Painter>, onItemClick: (String) -> Unit) {
    LazyColumn {
        itemsIndexed(items) { index, item ->
            ItemRow(
                text = item,
                painter = painters[index],
                onClick = { onItemClick(item) }
            )
        }
    }
}

@Composable
fun DateTimeWidget() {
    var currentTime by remember {
        mutableStateOf("")
    }
    var currentDate by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        while (true) {
            val now = LocalDateTime.now()
            currentDate = now.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
            currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm"))
            delay(1000L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentDate,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp),
            fontSize = 14.sp,
            fontFamily = typographica,
        )
        Text(
            text = currentTime,
            color = Color.White,
            fontSize = 52.sp,
            fontFamily = typographica
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(
    userInput: String,
    onUserInputChange: (String) -> Unit,
    onSendClick: () -> Unit,
    conversionType: String
) {
    val (inputLabel, inputUnit) = when (conversionType) {
        // Temperature conversions
        "Celsius to Fahrenheit" -> "Temperature (°C)" to "°C"
        "Fahrenheit to Celsius" -> "Temperature (°F)" to "°F"
        "Celsius to Kelvin" -> "Temperature (°C)" to "°C"
        "Kelvin to Celsius" -> "Temperature (K)" to "K"

        // Length conversions
        "Meters to Kilometers" -> "Length (m)" to "m"
        "Kilometers to Meters" -> "Length (km)" to "km"
        "Feet to Meters" -> "Length (ft)" to "ft"
        "Meters to Feet" -> "Length (m)" to "m"

        // Fluid conversions
        "Liters in Milliliters" -> "Volume (L)" to "L"
        "Milliliters in Liters" -> "Volume (mL)" to "mL"
        "Gallons in Liters" -> "Volume (gal)" to "gal"
        "Liters in Gallons" -> "Volume (L)" to "L"

        // Mass conversions
        "Kilograms in Grams" -> "Mass (kg)" to "kg"
        "Grams in Kilograms" -> "Mass (g)" to "g"
        "Pounds in Kilograms" -> "Mass (lb)" to "lb"
        "Kilograms in Pounds" -> "Mass (kg)" to "kg"

        // Speed conversions
        "km/h to mph" -> "Speed (km/h)" to "km/h"
        "mph to km/pr" -> "Speed (mph)" to "mph"

        else -> "Value" to ""
    }

    Row(
        modifier = Modifier
            .width(380.dp)
            .background(Color.White, shape = RoundedCornerShape(40.dp))
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = inputUnit,
            color = Color(0xff0097b2),
            textAlign = TextAlign.Center,
            fontFamily = montserattBold,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = userInput,
            onValueChange = onUserInputChange,
            label = {
                Text(
                    text = inputLabel,
                    fontFamily = montseratt
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            textStyle = TextStyle(
                fontFamily = montseratt,
                fontSize = 16.sp
            )
        )
        Box(
            modifier = Modifier
                .width(30.dp)
                .background(Color.Transparent)
                .clickable { onSendClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.message),
                contentDescription = "Send",
                tint = Color(0xff000000)
            )
        }
    }
}

@Composable
fun ConversionChat(
    chatMessages: List<Pair<String, String>>,
    selectedConversion: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = false
        ) {
            items(chatMessages) { message ->
                if (message.first == "user") {
                    UserChatBubble(message.second, selectedConversion)
                } else {
                    ResponseChatBubble(message.second, selectedConversion)
                }
            }
        }
    }
}

@Composable
fun UserChatBubble(message: String, selectedConversion: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = message,
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = montseratt,
            modifier = Modifier
                .background(Color(0xFF14ACE4), shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF14ACE4), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = selectedConversion[0].toString(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = typographica
            )
        }
    }
}

@Composable
fun ResponseChatBubble(message: String, selectedConversion: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF00BF63), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = selectedConversion[
                    selectedConversion
                        .lastIndexOf(" ") + 1
                ].toString(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = typographica
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = message,
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = montseratt,
            modifier = Modifier
                .background(Color(0xFF00BF63), shape = RoundedCornerShape(8.dp))
                .padding(8.dp)
        )
    }
}


/* Previews */

@Preview(showBackground = true)
@Composable
fun PreviewItemList() {
    val items = listOf("Length", "Temperature", "Fluids", "Speed", "Mass")
    val icons = listOf(
        R.drawable.ruler,
        R.drawable.thermometer,
        R.drawable.water,
        R.drawable.speed,
        R.drawable.scale
    )
    val painters: List<Painter> = icons.map { painterResource(id = it) }
    ItemList(items = items, painters = painters) {

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    LandingPage(context = null)
}