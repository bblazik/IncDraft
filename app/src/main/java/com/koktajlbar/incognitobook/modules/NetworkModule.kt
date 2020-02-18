package com.koktajlbar.incognitobook.modules

import com.google.gson.GsonBuilder
import com.koktajlbar.incognitobook.CocktailApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {
    @Provides
    @Reusable
    internal fun provideCocktailApi(retrofit: Retrofit) : CocktailApi {
        return retrofit.create(CocktailApi::class.java)
    }

    @Provides
    @Reusable
    internal fun provideRetrofit() : Retrofit {
        val api_url = "https://incognito.frelia.org/api/v1/"
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json").build()
                    chain.proceed(request)
                }.build()
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create()
        val builder = Retrofit.Builder()
                .baseUrl(api_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        return builder
    }
}