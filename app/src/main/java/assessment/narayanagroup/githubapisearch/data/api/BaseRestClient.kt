@file:Suppress("BlockingMethodInNonBlockingContext")

package assessment.narayanagroup.githubapisearch.data.api

import android.annotation.SuppressLint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit


abstract class BaseRestClient {



    private val client: OkHttpClient
        get() {

            val builder = OkHttpClient.Builder()

            builder
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                .followSslRedirects(true)
                .addInterceptor(AuthInterceptorResponse())

            return builder.build()
        }

    fun <T> getServer(cla: Class<T>, baseUrl: String): T {
        val builder = Retrofit.Builder()
        handleConverterFactory(builder)

        return builder.client(client)
            .baseUrl(baseUrl)
            .build()
            .create(cla)
    }

    abstract fun handleConverterFactory(builder: Retrofit.Builder)
}

class AuthInterceptorResponse : Interceptor {

    @Suppress("UNREACHABLE_CODE", "IMPLICIT_NOTHING_AS_TYPE_PARAMETER")
    @SuppressLint("ShowToast")
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

        val originalRequest = chain.request()
        val authenticationRequest = originalRequest.newBuilder()
            .addHeader("Accept", "application/vnd.github+json")
            .addHeader("Authorization", "Bearer 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")

        chain.proceed(authenticationRequest.build()).apply {
          /*  when {

                (code == TOKEN_CODE_401 || code == TOKEN_CODE_408)-> {

                }
                else -> return this
            }*/
            return this
        }
    }

}


