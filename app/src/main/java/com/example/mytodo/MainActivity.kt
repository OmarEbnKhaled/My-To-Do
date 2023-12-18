package com.example.mytodo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytodo.ui.MyToDoScreen
import com.example.mytodo.ui.theme.MyToDoTheme
import com.example.mytodo.ui.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyToDoTheme {
                MyToDoApp()
            }
        }
    }
}

@Composable
fun MyToDoApp() {
    MyToDoScreen()
}

@Preview(name = "Light Mode")
@Preview(name = " Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MyToDoAppPreview() {
    MyToDoTheme {
        MyToDoApp()
    }
}