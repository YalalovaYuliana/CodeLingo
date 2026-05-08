package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.yi_555555555.codelingo.R


@Composable
fun CourseCard(
  title: String,
  description: String?,
  iconUrl: String?,
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null,
  selected: Boolean = false
) {
  Box(
    modifier = modifier
      .border(
        width = 3.dp,
        color = if (selected) {
          MaterialTheme.colorScheme.primaryContainer
        } else MaterialTheme.colorScheme.outlineVariant,
        shape = RoundedCornerShape(13.dp)
      )
      .background(
        shape = RoundedCornerShape(13.dp),
        color = Color.Transparent
      )
      .clickable(
        enabled = onClick != null,
        onClick = { onClick?.invoke() }
      ),
    contentAlignment = Alignment.Center
  ) {
    Row(
      modifier = Modifier.padding(if (description != null) 16.dp else 4.dp),
      verticalAlignment = if (description != null) Alignment.Top else Alignment.CenterVertically
    ) {
      Column(
        modifier = Modifier
          .weight(2f)
          .padding(
            vertical = 16.dp
          ),
        horizontalAlignment = Alignment.Start
      ) {
        Text(
          modifier = Modifier.padding(start = if (description != null) 0.dp else 8.dp),
          text = title,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.secondary
        )
        if (description != null) {
          VSpacer(24.dp)
          Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiaryContainer
          )
        }
      }
      SubcomposeAsyncImage(
        modifier = Modifier
          .weight(1f)
          .size(80.dp)
          .padding(vertical = 12.dp),
        model = iconUrl,
        imageLoader = rememberImageLoader(),
        loading = {
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(20.dp),
              strokeWidth = 2.dp
            )
          }
        },
        error = {
          Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(R.drawable.course_temp),
            contentDescription = "course icon error"
          )
        },
        contentDescription = "course icon"
      )
    }
  }
}