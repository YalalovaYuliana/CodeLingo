package com.yi_555555555.codelingo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.screens.login.LoginScreen
import com.yi_555555555.codelingo.presentation.screens.main.MainScreen
import com.yi_555555555.codelingo.presentation.screens.onboarding.OnboardingScreen
import com.yi_555555555.codelingo.presentation.screens.profile.ProfileScreen
import com.yi_555555555.codelingo.presentation.screens.register.RegisterScreen
import kotlinx.serialization.Serializable

@Composable
fun NavGraph(
  navViewModel: NavViewModel = hiltViewModel()
) {

  val navController = rememberNavController()
  val hasAccessToken = navViewModel.hasAccessToken.collectAsState().value

  if (hasAccessToken == null) {
    LoadingState()
    return
  }

  val startDestination = if (hasAccessToken) {
    Screen.ProfileScreen
  } else Screen.OnboardingScreen

  NavHost(
    navController = navController,
    startDestination = startDestination
  ) {
    composable<Screen.OnboardingScreen> {
      OnboardingScreen(
        onRegisterClick = {
          navController.navigate(Screen.RegisterScreen)
        },
        onAlreadyHaveAccountClick = {
          navController.navigate(Screen.LoginScreen)
        }
      )
    }
    composable<Screen.RegisterScreen> {
      RegisterScreen(
        onSuccessRegister = {
          navController.navigate(Screen.ProfileScreen) {
            popUpTo(0) {
              inclusive = true
            }
          }
        },
        onAlreadyHaveAccountClick = {
          navController.navigate(Screen.LoginScreen)
        },
        onBackClick = {
          navController.navigateUp()
        }
      )
    }
    composable<Screen.LoginScreen> {
      LoginScreen(
        onSuccessLogin = {
          navController.navigate(Screen.ProfileScreen) {
            popUpTo(0) {
              inclusive = true
            }
            //launchSingleTop = true
          }
        },
        onBackClick = {
          navController.navigateUp()
        }
      )
    }
    composable<Screen.MainScreen> {
      MainScreen(
        onLogout = {
          navController.navigate(Screen.OnboardingScreen) {
            popUpTo(0) {
              inclusive = true
            }
          }
        }
      )
    }
    composable<Screen.ProfileScreen> {
      ProfileScreen(
        onLogout = {
          navController.navigate(Screen.OnboardingScreen) {
            popUpTo(0) {
              inclusive = true
            }
          }
        }
      )
    }
  }
}

sealed interface Screen {

  @Serializable
  data object OnboardingScreen : Screen

  @Serializable
  data object RegisterScreen : Screen

  @Serializable
  data object LoginScreen : Screen

  @Serializable
  data object MainScreen : Screen

  @Serializable
  data object ProfileScreen : Screen
}