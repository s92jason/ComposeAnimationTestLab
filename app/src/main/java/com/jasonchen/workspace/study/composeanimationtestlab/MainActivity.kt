package com.jasonchen.workspace.study.composeanimationtestlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jasonchen.workspace.study.composeanimationtestlab.ui.home.HomeUi
import com.jasonchen.workspace.study.composeanimationtestlab.ui.home.data.AnimationType.CIRCLE_PROGRESS
import com.jasonchen.workspace.study.composeanimationtestlab.ui.home.data.AnimationType.COMPLETE
import com.jasonchen.workspace.study.composeanimationtestlab.ui.home.data.animations
import com.jasonchen.workspace.study.composeanimationtestlab.ui.theme.ComposeAnimattionTestLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimattionTestLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
                    HomeUi(items = animations, onItemClock = {type ->
                        when(type) {
                            CIRCLE_PROGRESS -> {

                            }
                            COMPLETE -> {

                            }
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAnimattionTestLabTheme {
        Greeting("Android")
    }
}