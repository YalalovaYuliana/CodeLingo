package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.domain.model.Level
import com.yi_555555555.codelingo.presentation.mappers.getBackgroundColor
import com.yi_555555555.codelingo.presentation.mappers.getBackgroundShadowColor

@Composable
fun LevelCard(
  index: Int,
  level: Level,
  enabled: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
//    Text(
//      modifier = Modifier.widthIn(max = 200.dp),
//      text = level.title,
//      textAlign = TextAlign.Center,
//      style = MaterialTheme.typography.labelMedium,
//      color = if (level.isComplete) {
//        MaterialTheme.colorScheme.inversePrimary
//      } else MaterialTheme.colorScheme.secondary
//    )
//    VSpacer(8.dp)
    Column(
      modifier = Modifier
        .clip(RoundedCornerShape(24.dp))
        .clickable(
          enabled = enabled,
          onClick = onClick
        )
        .background(level.getBackgroundShadowColor(enabled)),
    ) {
      Box(
        modifier = Modifier
          .size(120.dp)
          .clip(RoundedCornerShape(24.dp))
          .background(level.getBackgroundColor(enabled)),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = index.toString(),
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.titleLarge,
          color = level.getBackgroundShadowColor(enabled)
        )
      }
      VSpacer(4.dp)
    }
  }
}