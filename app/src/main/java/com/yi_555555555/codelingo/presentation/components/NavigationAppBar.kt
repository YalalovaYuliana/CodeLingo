package com.yi_555555555.codelingo.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.presentation.navigation.HomeScreenContent

@Composable
fun NavigationAppBar(
  selectedBottomBarDestination: BottomBarDestination,
  onClick: (BottomBarDestination) -> Unit
) {
  NavigationBar(
    windowInsets = NavigationBarDefaults.windowInsets,
    containerColor = Color.Transparent
  ) {
    BottomBarDestination.entries.forEach { destination ->
      val selected = selectedBottomBarDestination == destination
      NavigationBarItem(
        selected = selected,
        onClick = {
          onClick(destination)
        },
        icon = {
          Column {
            Icon(
              modifier = Modifier.size(49.dp),
              painter = painterResource(destination.icon),
              contentDescription = destination.contentDescription,
              tint = Color.Unspecified
            )
            if (selected) {
              VSpacer(4.dp)
              HorizontalDivider(
                modifier = Modifier.width(49.dp),
                thickness = 3.dp,
                color = MaterialTheme.colorScheme.outline
              )
            }
          }
        },
        colors = NavigationBarItemDefaults.colors(
          indicatorColor = Color.Transparent
        )
      )
    }
  }
}

enum class BottomBarDestination(
  val content: HomeScreenContent,
  @DrawableRes val icon: Int,
  val contentDescription: String
) {
  Achievements(HomeScreenContent.Achievements, R.drawable.achievements_icon, "achievements screen"),
  Main(HomeScreenContent.Main, R.drawable.main_icon, "main screen"),
  Profile(HomeScreenContent.Profile, R.drawable.profile_icon, "profile screen")
}