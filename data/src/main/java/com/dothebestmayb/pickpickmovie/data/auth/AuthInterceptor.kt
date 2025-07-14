package com.dothebestmayb.pickpickmovie.data.auth

import com.dothebestmayb.pickpickmovie.data.auth.local.storage.SessionStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 모든 API 요청을 가로채 'Authorization: Bearer {token}' 헤더를 추가합니다.
 *
 * @param sessionStorageLazy Koin의 Lazy 델리게이트를 통해 SessionStorage를 주입받습니다.
 *      Lazy<SessionStorage>를 사용하는 이유는 Hilt의 Provider와 마찬가지로 **의존성 순환 방지**입니다.
 *
 *      OkHttpClient -> AuthInterceptor -> SessionStorage -> (토큰 갱신 시) OkHttpClient
 *      위와 같은 순환 참조에서, Lazy를 사용하면 OkHttpClient 생성 시점이 아닌,
 *      intercept() 메소드가 실제로 호출될 때 SessionStorage의 인스턴스를 요청(lazy initialization)하므로
 *      이 순환 고리를 끊을 수 있습니다.
 */
class AuthInterceptor(
    private val sessionStorageLazy: Lazy<SessionStorage>,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 토큰을 갱신하기 위한 API를 호출할 경우, 헤더 추가 없이 바로 요청
        if (request.url.encodedPath.contains("/auth/refresh")) {
            return chain.proceed(request)
        }

        val sessionStorage = sessionStorageLazy.value

        // runBlocking을 사용하여 suspend 함수를 동기적으로 호출합니다.
        // 현재 스레드(OkHttp의 I/O 스레드)는 get()이 완료될 때까지 여기서 대기합니다.
        val accessToken = runBlocking {
            sessionStorage.get()?.accessToken
        }

        if (accessToken.isNullOrEmpty()) {
            return chain.proceed(request)
        }
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }
}
