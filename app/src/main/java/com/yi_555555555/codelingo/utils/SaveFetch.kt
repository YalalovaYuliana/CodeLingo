package com.yi_555555555.codelingo.utils

import android.content.Context
import com.yi_555555555.codelingo.R
import retrofit2.HttpException
import java.io.IOException

suspend fun safeFetch(
  context: Context,
  onSuccess: suspend () -> Unit,
  onFailure: suspend (String) -> Unit
) {
  var errorMessage: String? = null
  try {
    onSuccess()
  } catch (e: HttpException) {
    errorMessage = e.code().getErrorMessageFromCode(context)
  } catch (_: IOException) {
    errorMessage = context.getString(R.string.check_internet_connection)
  } catch (_: Exception) {
    errorMessage = context.getString(R.string.something_went_wrong)
  }
  if (errorMessage != null) {
    onFailure(errorMessage)
  }
}