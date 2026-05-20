import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class GoogleAuthManager(
  private val context: Context,
  private val webClientId: String
) {
  private val credentialManager = CredentialManager.create(context)

  suspend fun signIn(): Result<String> {
    return try {
      val request = createSignInRequest()
      val response = credentialManager.getCredential(context, request)
      handleCredentialResponse(response)
    } catch (e: GetCredentialException) {
      Result.failure(e)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  private fun createSignInRequest(): GetCredentialRequest {
    val googleIdOption = GetGoogleIdOption.Builder()
      .setServerClientId(webClientId)
      .setFilterByAuthorizedAccounts(false) // Показываем все аккаунты
      .setAutoSelectEnabled(false) // Отключаем авто-выбор, показываем диалог
      .build()

    return GetCredentialRequest.Builder()
      .addCredentialOption(googleIdOption)
      .build()
  }

  private fun handleCredentialResponse(response: GetCredentialResponse): Result<String> {
    val credential = response.credential

    return when (credential.type) {
      GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
        try {
          val googleIdTokenCredential = GoogleIdTokenCredential
            .createFrom(credential.data)
          val authCode = googleIdTokenCredential.idToken

          if (authCode.isNotEmpty()) {
            Result.success(authCode)
          } else {
            Result.failure(Exception("Auth code is null"))
          }
        } catch (e: Exception) {
          Result.failure(e)
        }
      }

      else -> Result.failure(Exception("Unexpected credential type: ${credential.type}"))
    }
  }
}