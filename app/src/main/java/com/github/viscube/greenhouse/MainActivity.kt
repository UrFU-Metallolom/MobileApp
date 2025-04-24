package com.github.viscube.greenhouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.terrakok.modo.Modo.rememberRootScreen
import com.github.terrakok.modo.RootScreen
import com.github.terrakok.modo.animation.SlideTransition
import com.github.terrakok.modo.multiscreen.MultiScreen
import com.github.terrakok.modo.multiscreen.MultiScreenNavModel
import com.github.terrakok.modo.stack.DefaultStackScreen
import com.github.terrakok.modo.stack.StackNavModel
import com.github.viscube.greenhouse.deviceList.presentation.screens.ListScreen
import com.github.viscube.greenhouse.ui.theme.GreenhouseTheme
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val rootScreen: RootScreen<DefaultStackScreen> = rememberRootScreen {
                DefaultStackScreen(
                    StackNavModel(
                        MainScreen()
                    )
                )
            }
            GreenhouseTheme {
                Surface(color = Color.White) {
                    rootScreen.Content(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Suppress("CanBeParameter")
@Parcelize
class MainScreen(
    private val navModel: MultiScreenNavModel = MultiScreenNavModel(
        screens = listOf(ListScreen()),
        selected = 0
    )
) : MultiScreen(navModel) {
    @Composable
    override fun Content(modifier: Modifier) {
        Scaffold(modifier = modifier) { paddingValues ->
            SelectedScreen(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) { innerModifier ->
                SlideTransition(innerModifier)
            }
        }
    }
}