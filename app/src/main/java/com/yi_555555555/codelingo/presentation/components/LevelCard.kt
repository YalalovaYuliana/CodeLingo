package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.presentation.mappers.getBackgroundColor
import com.yi_555555555.codelingo.presentation.mappers.getBackgroundShadowColor
import com.yi_555555555.codelingo.presentation.mappers.getLevelIcon


@Composable
fun LevelCard(
  level: Level,
  enabled: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clip(RoundedCornerShape(50))
      .clickable(
        enabled = enabled,
        onClick = onClick
      )
      .background(level.getBackgroundShadowColor(enabled)),
  ) {
    Box(
      modifier = Modifier
        .size(110.dp)
        .clip(RoundedCornerShape(50))
        .background(level.getBackgroundColor(enabled)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        painter = painterResource(level.getLevelIcon(enabled)),
        tint = level.getBackgroundShadowColor(enabled),
        contentDescription = "level icon"
      )
    }
    VSpacer(6.dp)
  }
}