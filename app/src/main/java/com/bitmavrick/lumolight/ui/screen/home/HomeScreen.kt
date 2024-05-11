package com.bitmavrick.lumolight.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraRear
import androidx.compose.material.icons.filled.DynamicForm
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.outlined.CameraRear
import androidx.compose.material.icons.outlined.DynamicForm
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bitmavrick.lumolight.activity.core.Screen
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightScreen
import com.bitmavrick.lumolight.ui.tab.flashlight.FlashlightViewModel
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionScreen
import com.bitmavrick.lumolight.ui.tab.quickAction.QuickActionViewModel
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashScreen
import com.bitmavrick.lumolight.ui.tab.screenFlash.ScreenFlashViewModel
import com.bitmavrick.lumolight.ui.theme.LumolightTheme
import com.bitmavrick.lumolight.util.getAppVersion
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeUiState: HomeUiState,
    homeOnEvent: (HomeUiEvent) -> Unit,
    quickActionViewModel : QuickActionViewModel = viewModel(),
    screenFlashViewModel : ScreenFlashViewModel = viewModel(),
    flashlightViewModel : FlashlightViewModel = viewModel()
) {
    val tabItems = listOf(
        TabItem(
            title = "Actions",
            unselectedIcon = Icons.Outlined.DynamicForm,
            selectedIcon = Icons.Filled.DynamicForm
        ),
        TabItem(
            title = "Screen",
            unselectedIcon = Icons.Outlined.Smartphone,
            selectedIcon = Icons.Filled.PhoneIphone
        ),
        TabItem(
            title = "Flashlight",
            unselectedIcon = Icons.Outlined.CameraRear,
            selectedIcon = Icons.Filled.CameraRear
        ),
    )

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            HomeScreenTopBar(
                onClickSettings = {
                    // navController.navigate(Screen.SettingScreen.route)
                    exitProcess(0)
                },
                onClickAbout = {
                    homeOnEvent(HomeUiEvent.updateShowAboutDialog(true))
                }
            )
        },
        content = { innerPadding ->
            val pagerState = rememberPagerState {
                tabItems.size
            }

            if(homeUiState.showAboutDialog){
                AlertDialog(
                    title = {
                        Text(text = "Lumolight")
                    },
                    text = {
                        Text(text = "Version: ${getAppVersion(LocalContext.current)}\nDeveloped by BitMavrick")
                    },
                    onDismissRequest = { homeOnEvent(HomeUiEvent.updateShowAboutDialog(false))  },
                    confirmButton = {
                        TextButton(
                            onClick = { homeOnEvent(HomeUiEvent.updateShowAboutDialog(false)) }
                        ) {
                            Text(text = "OK")
                        }
                    }
                )
            }

            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if(!pagerState.isScrollInProgress){
                    homeOnEvent(HomeUiEvent.updateTab(pagerState.currentPage))
                }
            }

            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                CustomTabRow(
                    selectedTabIndex = homeUiState.selectedTabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                        )
                    }
                ) {
                    tabItems.forEachIndexed{index, item ->
                        LeadingIconTab(
                            selected = index == homeUiState.selectedTabIndex,
                            onClick = {
                                homeOnEvent(HomeUiEvent.updateTab(index))
                                coroutineScope.launch { pagerState.animateScrollToPage(index) }
                            },
                            text = {
                                Text(
                                    text = item.title,
                                    maxLines = 1
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = if(index == homeUiState.selectedTabIndex){
                                        item.selectedIcon
                                    } else item.unselectedIcon ,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {index ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        when(index){
                            0 -> QuickActionScreen(
                                navController = navController,
                                viewModel = quickActionViewModel,
                                snakeBarHost = snackBarHostState
                            )
                            1 -> ScreenFlashScreen(
                                viewModel = screenFlashViewModel,
                                onClickStart = { navController.navigate(Screen.FlashScreen.route) }
                            )
                            2 -> FlashlightScreen(
                                viewModel = flashlightViewModel
                            )
                        }
                    }
                }
            }
        },

        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    onClickSettings: () -> Unit,
    onClickAbout : () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = "Lumolight",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {

            /* ! SOS feature will not be implemented in first release
            IconButton(
                onClick = { /* TODO */ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Sos,
                    contentDescription = null
                )
            }
            */

            IconButton(
                onClick = { onClickSettings() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.PowerSettingsNew,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = null
                )
            }


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                DropdownMenuItem(
                    text = {
                        Text(text = "About")
                    },
                    onClick = { onClickAbout() },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Info,
                            contentDescription = null)
                    }
                )

                /*
                DropdownMenuItem(
                    text = {
                        Text(text = "Feedback")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.Comment,
                            contentDescription = null)
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Share")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Share,
                            contentDescription = null)
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "About us")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Info,
                            contentDescription = null)
                    }
                )

                HorizontalDivider()

                DropdownMenuItem(
                    text = {
                        Text(text = "Rate Lumolight")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.StarRate,
                            contentDescription = null)
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Try more apps")
                    },
                    onClick = { /*TODO*/ },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Spoke,
                            contentDescription = null)
                    }
                )

                 */
            }
        }
    )
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    LumolightTheme {
        HomeScreen(
            navController = rememberNavController(),
            homeUiState = HomeUiState(),
            homeOnEvent = {}
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.pagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
    pageIndexMapping: (Int) -> Int = { it },
) = layout { measurable, constraints ->
    if (tabPositions.isEmpty()) {
        layout(constraints.maxWidth, 0) {}
    } else {
        val currentPage = minOf(tabPositions.lastIndex, pageIndexMapping(pagerState.currentPage))
        val currentTab = tabPositions[currentPage]
        val previousTab = tabPositions.getOrNull(currentPage - 1)
        val nextTab = tabPositions.getOrNull(currentPage + 1)
        val fraction = pagerState.currentPageOffsetFraction
        val indicatorWidth = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.width, nextTab.width, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.width, previousTab.width, -fraction).roundToPx()
        } else {
            currentTab.width.roundToPx()
        }
        val indicatorOffset = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.left, nextTab.left, fraction).roundToPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.left, previousTab.left, -fraction).roundToPx()
        } else {
            currentTab.left.roundToPx()
        }
        val placeable = measurable.measure(
            Constraints(
                minWidth = indicatorWidth,
                maxWidth = indicatorWidth,
                minHeight = 0,
                maxHeight = constraints.maxHeight
            )
        )
        layout(constraints.maxWidth, maxOf(placeable.height, constraints.minHeight)) {
            placeable.placeRelative(
                indicatorOffset,
                maxOf(constraints.minHeight - placeable.height, 0)
            )
        }
    }
}