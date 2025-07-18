package ru.null_checkers.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitHelper {

    fun creatExhibitionRetrofit(): ExhibitionApi {
        return creatRetrofit().create(ExhibitionApi::class.java)
    }

    fun creatPhotosRetrofit(): PhotoApi {
        return creatRetrofit().create(PhotoApi::class.java)
    }

    fun creatRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                try {
                    val request = chain.request()
                    println("Request URL: ${request.url}")
                    val response = chain.proceed(request)
                    response
                } catch (e: Exception) {
                    println("Network error: ${e.message}")
                    throw e
                }
            }
            .build()

        val base_url = "https://serebrogallery.ru/"
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            //.client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        return retrofit
    }
}
