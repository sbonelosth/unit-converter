package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                val navController = rememberNavController()
                // AppNavigation(navController = navController)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LandingPage(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "landing_screen") {
        composable("landing_screen") { LandingPage(navController) }
        composable("length_screen") { LengthScreen() }
        composable("temp_screen") { }
        composable("fluids_screen") { }
        composable("speed_screen") { }
        composable("mass_screen") { }
    }
}

@Composable
fun LandingPage(navController: NavHostController, modifier: Modifier = Modifier) {

    val appBg: Painter = painterResource(R.drawable.app_bg)
    val context = LocalContext.current as ComponentActivity

    Box(modifier = modifier) {
        Image(
            painter = appBg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateTimeWidget()
            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(4.dp)
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
                when (item) {
                    "Length" -> Toast.makeText(context, "$item Clicked", Toast.LENGTH_SHORT).show()
                    "Temperature" -> Toast.makeText(context, "$item Clicked", Toast.LENGTH_SHORT).show()
                    "Fluids" -> Toast.makeText(context, "$item Clicked", Toast.LENGTH_SHORT).show()
                    "Speed" -> Toast.makeText(context, "$item Clicked", Toast.LENGTH_SHORT).show()
                    "Mass" -> Toast.makeText(context, "$item Clicked", Toast.LENGTH_SHORT).show()
                }
            }
            Button(
                onClick = { context.finish() },
                modifier = modifier
                    .background(Color.Red, shape = CircleShape)
                    .width(50.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F8FF)),
                shape = CircleShape
            ) {
                Text(
                    text = "❌",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@Composable
fun ItemRow(text: String, painter: Painter, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xff0097b2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text[0].toString(),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = modifier
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
                modifier = Modifier
                    .clickable { onItemClick(item) }
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
            fontSize = 14.sp
        )
        Text(
            text = currentTime,
            color = Color.White,
            fontSize = 52.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun LengthScreen() {
    Text(text = "Length Conversion Activity")
}

/* Previews */

@Preview(showBackground = true)
@Composable
fun LandingPagePreview() {
    val navController = rememberNavController()
    AppNavigation(navController = navController)
    UnitConverterTheme {
        LandingPage(navController = navController, modifier = Modifier)
    }
}

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