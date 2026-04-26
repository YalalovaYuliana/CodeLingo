package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.model.UserLevel
import com.yi_555555555.codelingo.presentation.mappers.getBackgroundColor

@Composable
fun LevelCard(
  level: UserLevel,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .size(120.dp)
      .clip(CircleShape)
      .clickable(
        onClick = onClick
      )
      .background(level.getBackgroundColor()),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      painter = painterResource(R.drawable.ic_education_cap),
      contentDescription = "level",
      tint = if (level.isComplete) Color.Unspecified else MaterialTheme.colorScheme.tertiary
    )
  }
}