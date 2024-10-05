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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LandingPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LandingPage(modifier: Modifier = Modifier) {
    val appBg: Painter = painterResource(R.drawable.app_bg)

    Box(modifier = modifier) {
        Image(
            painter = appBg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxHeight()
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
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
                    "Length" -> OpenActivity()
                    "Temperature" -> OpenActivity()
                    "Fluids" -> OpenActivity()
                    "Speed" -> OpenActivity()
                    "Mass" -> OpenActivity()
                }
            }
            Button(
                onClick = {  },
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

fun OpenActivity() {

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
fun TemperatureScreen(modifier: Modifier = Modifier) {
    val appBg: Painter = painterResource(R.drawable.app_bg)
    Box(modifier = modifier) {
        Image(
            painter = appBg,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxHeight()
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateTimeWidget()

            Text(
                text = "Temperature",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(4.dp)
            )

            DropdownMenuComponent(labelText = "Celcius to Fahrenheit")

            var text by remember { mutableStateOf("") }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(40.dp))
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "C",
                        color = Color(0xff0097b2),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(4.dp)
                    )
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Enter text") },
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    IconButton(
                        onClick = { /* Handle send action */ },
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.Transparent, shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.message),
                            contentDescription = "Send",
                            tint = Color(0xff000000)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuComponent(labelText: String) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(labelText) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        TextField(
            value = selectedOption,
            onValueChange = { selectedOption = it },
            label = { Text(text = labelText) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
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
fun ActivityPreview(){
    TemperatureScreen(modifier = Modifier)
}

@Preview (showBackground = true)
@Composable
fun MainPreview() {
    val navController = rememberNavController()
    LandingPage(modifier = Modifier)
}