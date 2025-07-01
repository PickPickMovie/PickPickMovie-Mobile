package com.dothebestmayb.pickpickmovie.data.auth

import com.dothebestmayb.pickpickmovie.data.auth.local.storage.SessionStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

/**
 * 모든 API 요청을 가로채 'Authorization: Bearer {token}' 헤더를 추가합니다.
 *
 * @param sessionStorageProvider Hilt의 Provider를 통해 SessionStorage를 주입받습니다.
 *      Provider<SessionStorage>를 사용하는 이유는 **의존성 순환(Dependency Cycle) 방지**입니다.
 *
 *      만약 SessionStorage를 직접 주입받을 경우, 다음과 같은 순환 참조가 발생할 수 있습니다:
 *      1. `OkHttpClient`가 생성되려면 `AuthInterceptor`가 필요함
 *      2. `AuthInterceptor`가 생성되려면 `SessionStorage`가 필요함
 *      3. `SessionStorage` 구현체가 토큰 갱신 로직 등으로 인해 다시 `OkHttpClient`를 필요로 함
 *
 *      Provider를 사용하면 `OkHttpClient` 생성 시점이 아닌, `intercept()` 메소드가 실제로
 *      호출될 때 `SessionStorage`의 인스턴스를 요청(lazy initialization)하므로
 *      이 순환 고리를 끊을 수 있습니다.
 */
class AuthInterceptor @Inject constructor(
    private val sessionStorageProvider: Provider<SessionStorage>,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 토큰을 갱신하기 위한 API를 호출할 경우, 헤더 추가 없이 바로 요청
        if (request.url.encodedPath.contains("/auth/refresh")) {
            return chain.proceed(request)
        }

        val sessionStorage = sessionStorageProvider.get()

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
