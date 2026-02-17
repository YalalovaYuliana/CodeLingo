package com.yi_555555555.codelingo.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yi_555555555.codelingo.R

// Set of Material typography styles to start with

val monserratFamily = FontFamily(
  Font(R.font.montserrat_regular, FontWeight.Normal),
  Font(R.font.montserrat_bold, FontWeight.Bold)
)

val robotoFamily = FontFamily(
  Font(R.font.roboto_medium, FontWeight.Medium)
)

val Typography = Typography(
  titleMedium = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 26.sp
  ),

  bodyLarge = TextStyle(
    fontFamily = robotoFamily,
    fontWeight = FontWeight.Medium,
    lineHeight = 16.sp,
    fontSize = 16.sp
  ),

  bodyMedium = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
  ),

  bodySmall = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp
  ),

  labelMedium = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp
  )
)