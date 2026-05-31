package com.example.catalogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.catalogapp.theme.CatalogAppTheme
import com.example.catalogapp.ui.navigation.MainNavigation
import com.example.catalogapp.ui.viewmodels.ThemeViewModel
import com.example.catalogapp.data.local.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  private val themeViewModel: ThemeViewModel by viewModels()
  private lateinit var preferencesManager: PreferencesManager

  private var startDestination by mutableStateOf<String?>(null)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    preferencesManager = PreferencesManager(this)

    lifecycleScope.launch {
      val isOnboardingCompleted = preferencesManager.onboardingCompleted.first()
      startDestination = if (isOnboardingCompleted) "home" else "onboarding"
    }

    enableEdgeToEdge()
    setContent {
      val isDarkMode by themeViewModel.isDarkMode.collectAsState()

      if (startDestination == null) {
        // Mostrar splash screen o simplemente no dibujar nada hasta saber el destino
        return@setContent
      }

      CatalogAppTheme(darkTheme = isDarkMode) { 
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) { 
            MainNavigation(themeViewModel = themeViewModel, initialDestination = startDestination!!) 
        } 
      }
    }
  }
}
