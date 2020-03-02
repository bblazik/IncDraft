package com.koktajlbar.incognitobook.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.koktajlbar.incognitobook.CocktailApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val koinNetworkModule = module {
    factory { provideOkHttpClient() }
    factory { provideGson() }
    single { provideRetrofit(get(), get()) }
    factory { provideCocktailApi(get()) }
}

internal fun provideCocktailApi(retrofit: Retrofit) : CocktailApi {
    return retrofit.create(CocktailApi::class.java)
}

internal fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson) : Retrofit {
    val api_url = "https://incognito.frelia.org/api/v1/"
    return Retrofit.Builder()
            .baseUrl(api_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}

internal fun provideGson() : Gson {
    return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()
}

internal fun provideOkHttpClient() : OkHttpClient {
    return OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json").build()
                chain.proceed(request)
            }.build()
}

