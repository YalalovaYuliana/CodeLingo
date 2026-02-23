package com.yi_555555555.codelingo.presentation.screens.validation

fun String.isValidEmail(): Boolean {
  val pattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
  return pattern.matches(this.trim())
}

fun String.isValidPassword(): Boolean {
  val containsUpperCaseLetters = this.find { it in ('A'..'Z') } != null
  val containsLowerCaseLetters = this.find { it in ('a'..'z') } != null
  val containsDigit = this.find { it.isDigit() } != null
  val containsSpecialSymbol = this.find { "!@#$%^&*".contains(it) } != null
  val hasEnoughLength = this.length >= 8
  return containsUpperCaseLetters && containsLowerCaseLetters &&
    containsDigit && containsSpecialSymbol && hasEnoughLength
}