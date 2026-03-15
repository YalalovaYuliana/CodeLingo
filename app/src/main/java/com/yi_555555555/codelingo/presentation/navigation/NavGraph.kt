package com.yi_555555555.codelingo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.screens.courses.CoursesScreen
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
  val state = navViewModel.state.collectAsState().value

  if (state == null) {
    LoadingState()
    return
  }

  val startDestination = if (state.hasAccessToken == true) {
    if (state.hasSelectedCourseId == true) {
      Screen.ProfileScreen
    } else Screen.CoursesScreen
  } else Screen.OnboardingScreen

  println("yuliana start destination: $startDestination")

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
          navController.navigate(Screen.CoursesScreen) {
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
          if (state.hasSelectedCourseId == true) {
            navController.navigate(Screen.ProfileScreen) {
              popUpTo(0) {
                inclusive = true
              }
            }
          } else {
            navController.navigate(Screen.CoursesScreen) {
              popUpTo(0) {
                inclusive = true
              }
            }
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
    composable<Screen.CoursesScreen> {
      CoursesScreen(
        onSuccessStart = {
          navController.navigate(Screen.ProfileScreen) {
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

  @Serializable
  data object CoursesScreen : Screen
}