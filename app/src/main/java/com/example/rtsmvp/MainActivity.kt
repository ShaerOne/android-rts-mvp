// Основные файлы MVP Android RTS игры на Kotlin с Jetpack Compose

// MainActivity.kt
package com.example.rtsmvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rtsmvp.ui.theme.RtsMvpTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RtsMvpTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen() {
    var metal by remember { mutableStateOf(1000) }
    var minerals by remember { mutableStateOf(1000) }
    var gas by remember { mutableStateOf(1000) }

    var metalMineLevel by remember { mutableStateOf(1) }
    var researchLevel by remember { mutableStateOf(0) }

    // Добыча ресурсов - каждые 5 секунд добавляем ресурсы согласно уровню шахты
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            metal += metalMineLevel * 100
            minerals += metalMineLevel * 50
            gas += metalMineLevel * 20
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Ресурсы:")
        Text(text = "Металл: $metal")
        Text(text = "Минералы: $minerals")
        Text(text = "Газ: $gas")

        Spacer(Modifier.height(24.dp))

        Text(text = "Уровень Металлургической шахты: $metalMineLevel")

        Button(
            onClick = {
                val upgradeCostMetal = metalMineLevel * 1000
                val upgradeCostMinerals = metalMineLevel * 500
                val upgradeCostGas = metalMineLevel * 300
                if (metal >= upgradeCostMetal && minerals >= upgradeCostMinerals && gas >= upgradeCostGas) {
                    metal -= upgradeCostMetal
                    minerals -= upgradeCostMinerals
                    gas -= upgradeCostGas
                    metalMineLevel += 1
                }
            }
        ) {
            Text(text = "Улучшить шахту")
        }

        Spacer(Modifier.height(24.dp))

        Text(text = "Исследования:")
        Text(text = if (researchLevel == 0) "Не исследовано" else "Уровень: $researchLevel")

        Button(onClick = {
            val researchCostMetal = 2000
            val researchCostMinerals = 1500
            val researchCostGas = 1000
            if (metal >= researchCostMetal && minerals >= researchCostMinerals && gas >= researchCostGas) {
                metal -= researchCostMetal
                minerals -= researchCostMinerals
                gas -= researchCostGas
                researchLevel = 1
            }
        }) {
            Text(text = "Исследовать количество флота")
        }

        Spacer(Modifier.height(24.dp))

        Text(text = "Флот:")
        var fleetCount by remember { mutableStateOf(0) }
        Text(text = "Корабли: $fleetCount")

        Button(onClick = {
            val shipMetalCost = 100
            val shipMineralsCost = 50
            val shipGasCost = 25
            if (metal >= shipMetalCost && minerals >= shipMineralsCost && gas >= shipGasCost) {
                metal -= shipMetalCost
                minerals -= shipMineralsCost
                gas -= shipGasCost
                fleetCount += 1
            }
        }) {
            Text(text = "Построить корабль")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RtsMvpTheme {
        GameScreen()
    }
}

// ui/theme/Theme.kt
package com.example.rtsmvp.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFBB86FC),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5),
    tertiary = androidx.compose.ui.graphics.Color(0xFF3700B3)
)

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF6200EE),
    secondary = androidx.compose.ui.graphics.Color(0xFF03DAC5),
    tertiary = androidx.compose.ui.graphics.Color(0xFF3700B3)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RtsMvpTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
