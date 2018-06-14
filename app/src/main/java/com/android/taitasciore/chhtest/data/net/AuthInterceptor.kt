package com.android.taitasciore.chhtest.data.net

import com.android.taitasciore.chhtest.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This class intercepts HTTP requests containing /portfolio as part of the URL.
 * In those cases, an authorization header is added to the request
 */
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val request = chain!!.request()
        var newRequest = request

        if (request.url().toString().contains("/portfolio"))
            newRequest = request.newBuilder()
                .addHeader("Accept", "'application/json'")
                .addHeader("Authorization", BuildConfig.AUTH_HEADER)
                .build()

        return chain!!.proceed(newRequest)
    }
}