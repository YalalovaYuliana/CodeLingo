package com.yi_555555555.codelingo.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yi_555555555.codelingo.presentation.components.BottomBarDestination
import com.yi_555555555.codelingo.presentation.components.LoadingState
import com.yi_555555555.codelingo.presentation.components.NavigationAppBar
import com.yi_555555555.codelingo.presentation.components.ScreenScaffold
import com.yi_555555555.codelingo.presentation.screens.achievments.AchievmentsScreen
import com.yi_555555555.codelingo.presentation.screens.courses.CoursesScreen
import com.yi_555555555.codelingo.presentation.screens.forgot_password_screens.input_email.InputEmailScreen
import com.yi_555555555.codelingo.presentation.screens.forgot_password_screens.reset_password.ResetPasswordScreen
import com.yi_555555555.codelingo.presentation.screens.forgot_password_screens.verify_code.VerifyCodeScreen
import com.yi_555555555.codelingo.presentation.screens.level.LevelScreen
import com.yi_555555555.codelingo.presentation.screens.login.LoginScreen
import com.yi_555555555.codelingo.presentation.screens.main.MainScreen
import com.yi_555555555.codelingo.presentation.screens.onboarding.OnboardingScreen
import com.yi_555555555.codelingo.presentation.screens.profile.ProfileScreen
import com.yi_555555555.codelingo.presentation.screens.register.RegisterScreen
import com.yi_555555555.codelingo.presentation.screens.settings.SettingsScreen
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
      Screen.HomeScreen
    } else Screen.CoursesScreen
  } else Screen.OnboardingScreen

  var selectedBottomBarDestination by rememberSaveable { mutableStateOf(BottomBarDestination.Main) }

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
        onSuccessLogin = { hasSelectedCourseId ->
          selectedBottomBarDestination = BottomBarDestination.Main
          if (hasSelectedCourseId) {
            navController.navigate(Screen.HomeScreen) {
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
          navController.navigate(Screen.OnboardingScreen) {
            popUpTo(0) {
              inclusive = true
            }
          }
        },
        onForgotPasswordClick = { email ->
          navController.navigate(Screen.InputEmailScreen(email))
        }
      )
    }
    composable<Screen.InputEmailScreen> {
      InputEmailScreen(
        onStartVerifyCode = { email ->
          navController.navigate(Screen.VerifyForgotPasswordScreen(email))
        },
        onBackClick = {
          navController.navigateUp()
        }
      )
    }
    composable<Screen.VerifyForgotPasswordScreen> {
      VerifyCodeScreen(
        onSuccessSendVerifyCode = { email, code ->
          navController.navigate(
            Screen.ResetPasswordScreen(
              email = email,
              code = code
            )
          )
        },
        onBackClick = {
          navController.navigate(Screen.LoginScreen)
        }
      )
    }
    composable<Screen.ResetPasswordScreen> {
      ResetPasswordScreen(
        onSuccessReset = {
          navController.navigate(Screen.LoginScreen)
        },
        onBackClick = {
          navController.navigate(Screen.OnboardingScreen) {
            popUpTo(0) {
              inclusive = true
            }
          }
        }
      )
    }
    composable<Screen.HomeScreen> {
      ScreenScaffold(
        bottomBar = {
          NavigationAppBar(
            selectedBottomBarDestination = selectedBottomBarDestination,
            onClick = { destination ->
              //navController.navigate(destination.content) // todo fix bottom bar navigation add another nav graph
              selectedBottomBarDestination = destination
            }
          )
        }
      ) { innerPadding ->
        when (selectedBottomBarDestination.content) {
          HomeScreenContent.Main -> {
            MainScreen(
              modifier = Modifier.padding(innerPadding),
              onLevelClick = { level ->
                navController.navigate(Screen.LevelScreen(level.id, level.title, level.isComplete))
              }
            )
          }

          HomeScreenContent.Profile -> {
            ProfileScreen(
              modifier = Modifier.padding(innerPadding),
              onSettingsClick = {
                navController.navigate(Screen.SettingsScreen)
              }
            )
          }

          HomeScreenContent.Achievements -> {
            AchievmentsScreen(
              modifier = Modifier.padding(innerPadding)
            )
          }
        }
      }
    }
    composable<Screen.CoursesScreen> {
      CoursesScreen(
        onSuccessStart = {
          selectedBottomBarDestination = BottomBarDestination.Main
          navController.navigate(Screen.HomeScreen) {
            popUpTo(0) {
              inclusive = true
            }
          }
        }
      )
    }
    composable<Screen.LevelScreen> {
      LevelScreen(
        onBackClick = {
          navController.navigateUp()
        }
      )
    }
    composable<Screen.SettingsScreen> {
      SettingsScreen(
        onBackClick = {
          navController.navigateUp()
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
  data object CoursesScreen : Screen

  @Serializable
  data object HomeScreen : Screen

  @Serializable
  data object SettingsScreen : Screen

  @Serializable
  data class LevelScreen(
    val levelId: Int,
    val title: String,
    val isComplete: Boolean
  ) : Screen

  @Serializable
  data class InputEmailScreen(
    val email: String
  ) : Screen

  @Serializable
  data class VerifyForgotPasswordScreen(
    val email: String
  ) : Screen

  @Serializable
  data class ResetPasswordScreen(
    val email: String,
    val code: String
  ) : Screen
}

enum class HomeScreenContent {
  Main,
  Profile,
  Achievements
}
