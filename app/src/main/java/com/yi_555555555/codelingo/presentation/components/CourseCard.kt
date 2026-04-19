package com.yi_555555555.codelingo.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yi_555555555.codelingo.R
import com.yi_555555555.codelingo.domain.model.Course
import com.yi_555555555.codelingo.presentation.ui.theme.CodeLingoTheme


@Composable
fun CourseCard(
  course: Course,
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null,
  showDescription: Boolean = false,
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
      modifier = Modifier.padding(if (showDescription) 16.dp else 4.dp),
      verticalAlignment = if (showDescription) Alignment.Top else Alignment.CenterVertically
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
          modifier = Modifier.padding(start = 8.dp),
          text = course.title,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.secondary
        )
        if (showDescription && course.description != null) {
          VSpacer(24.dp)
          Text(
            text = course.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
          )
        }
      }
      Image(
        modifier = Modifier
          .weight(1f)
          .widthIn(min = 109.dp, max = 154.dp),
        painter = painterResource(R.drawable.course_temp),
        contentDescription = "course",
        contentScale = ContentScale.FillWidth
      )
    }
  }
}


@Composable
@Preview(showSystemUi = true)
private fun CourseCardPreview() {
  CodeLingoTheme {
    Column {
      CourseCard(
        modifier = Modifier.fillMaxWidth(),
        course = Course(
          id = 1,
          title = "Python",
          description = "это скриптовый язык программирования. Он универсален, поэтому подходит для решения разнообразных задач и для многих платформ: начиная с iOS и Android и заканчивая серверными операционными системами."
        ),
        onClick = {},
        showDescription = true,
        selected = true
      )
      CourseCard(
        modifier = Modifier.fillMaxWidth(),
        course = Course(
          id = 1,
          title = "Python",
          description = "это скриптовый язык программирования. Он универсален, поэтому подходит для решения разнообразных задач и для многих платформ: начиная с iOS и Android и заканчивая серверными операционными системами."
        ),
        onClick = {}
      )
    }

  }
}