package com.yi_555555555.codelingo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yi_555555555.codelingo.presentation.navigation.NavGraph
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      CodeLingoTheme(darkTheme = false) {
        NavGraph()
      }
    }
  }
}