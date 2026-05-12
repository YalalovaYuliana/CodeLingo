package com.yi_555555555.codelingo.utils

import android.content.Context
import com.yi_555555555.codelingo.R

fun Int.getErrorMessageFromCode(context: Context): String {
  return when (this) {
    400 -> "Неверный код"
    401 -> "Неверный логин или пароль"
    404 -> "Функционал ещё не реализован"
    409 -> "Email уже зарегистрирован"
    else -> context.getString(R.string.something_went_wrong)
  }
}