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
  Font(R.font.montserrat_bold, FontWeight.Bold),
  Font(R.font.montserrat_semibold, FontWeight.SemiBold),
)

val robotoFamily = FontFamily(
  Font(R.font.roboto_medium, FontWeight.Medium)
)

val sansCodeFamily = FontFamily(
  Font(R.font.google_sans_code_regular, FontWeight.Normal)
)

val Typography = Typography(
  titleLarge = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 40.sp
  ),


  titleMedium = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 26.sp
  ),

  titleSmall = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 22.sp
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

  labelSmall = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.Normal,
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
  ),

  displaySmall = TextStyle(
    fontFamily = sansCodeFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 19.sp
  ),

  displayMedium = TextStyle(
    fontFamily = monserratFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp
  )
)