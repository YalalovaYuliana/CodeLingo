package com.yi_555555555.codelingo.utils

import android.content.Context
import com.yi_555555555.codelingo.R

fun Int.getErrorMessageFromCode(context: Context): String {
  return when (this) {
    401 -> "Неверный логин или пароль"
    409 -> "Email уже зарегистрирован"
    else -> context.getString(R.string.something_went_wrong)
  }
}