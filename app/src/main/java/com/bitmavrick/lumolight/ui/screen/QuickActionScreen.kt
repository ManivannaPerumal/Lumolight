package com.bitmavrick.lumolight.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bitmavrick.lumolight.ui.components.QuickSOSButton
import com.bitmavrick.lumolight.ui.components.QuickStartButton

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun QuickActionScreen() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // For segmented button
        var selectedIndex by remember { mutableIntStateOf(0) }
        val options = listOf("Front", "Both", "Back")

        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ){
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp)
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuickStartButton()
        }

        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ){
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex
                        ) {
                            Text(label)
                        }
                    }
                }

                QuickSOSButton()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
/*
@Preview(
    // The Landscape preview
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
*/
@Composable
fun QuickActionScreenLandScape() {

    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Front", "Both", "Back")

    Column(
        Modifier.fillMaxSize()
    ) {
        Row(
            Modifier
                .weight(1f, true)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp)
            )
        }

        Row(
            Modifier
                .weight(3.5f, true)
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedButton(
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "SOS",
                    )
                }


                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex
                        ) {
                            Text(label)
                        }
                    }
                }

            }

            Column(
                Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    Modifier.padding(8.dp)
                ) {
                    QuickStartButton()
                }
            }
        }

        Row(
            Modifier
                .weight(1f, true)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "This is the Ads section")
        }

    }

}