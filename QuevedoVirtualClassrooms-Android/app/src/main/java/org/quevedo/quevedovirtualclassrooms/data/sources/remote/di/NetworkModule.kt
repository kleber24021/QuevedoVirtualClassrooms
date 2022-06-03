package org.quevedo.quevedovirtualclassrooms.data.sources.remote.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.DataConsts
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.SessionManager
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.services.ClassroomService
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.services.ResourceService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .add(LocalDateTimeAdapter())
            .build()
        return MoshiConverterFactory
            .create(moshi)
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DataConsts.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideVideoService(retrofit: Retrofit): ResourceService =
        retrofit.create(ResourceService::class.java)

    @Singleton
    @Provides
    fun provideClassroomService(retrofit: Retrofit): ClassroomService =
        retrofit.create(ClassroomService::class.java)

    @Singleton
    @Provides
    fun provideAuthRequestInterceptor(
        sessionManager: SessionManager
    ): Interceptor {
        return Interceptor { chain ->
            val request = if (sessionManager.isLogged()) {
                chain.request().newBuilder()
                    .header("Authorization", "Bearer ${sessionManager.fetchAuthToken()}")
                    .build()
            } else {
                chain.request().newBuilder()
                    .header(
                        "Authorization",
                        "Basic ${sessionManager.username}:${sessionManager.password}"
                    )
                    .build()
            }
            val response = chain.proceed(request)
            val newToken  = response.header("Authorization")
            if (newToken != null && newToken.isNotBlank()){
                sessionManager.saveAuthToken(newToken)
            }
            return@Interceptor response;
        }
    }


    @Singleton
    @Provides
    fun providerSessionManager(): SessionManager = SessionManager()

    @Named(DataConsts.IO_DISPATCHER)
    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

}