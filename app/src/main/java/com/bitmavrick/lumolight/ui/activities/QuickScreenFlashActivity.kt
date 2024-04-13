package com.bitmavrick.lumolight.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.ui.utils.KeepScreenOn
import com.bitmavrick.lumolight.ui.utils.UpdateBrightness

@Suppress("DEPRECATION")
class QuickScreenFlashActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            UpdateBrightness()
            KeepScreenOn()
            QuickScreenFlash(onExit = { onBackPressed() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuickScreenFlash(onExit: () -> Unit = {}){
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedButton(onClick = { onExit()}) {
            Text(
                text = "EXIT",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


