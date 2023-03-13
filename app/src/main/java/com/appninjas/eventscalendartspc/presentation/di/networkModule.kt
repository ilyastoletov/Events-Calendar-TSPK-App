package com.appninjas.eventscalendartspc.presentation.di

import com.appninjas.data.network.ApiService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single<FirebaseFirestore> {
        Firebase.firestore
    }

    single<FirebaseAuth> {
        Firebase.auth
    }

    single<Retrofit> {
        provideRetrofit()
    }

    single<ApiService> {
        provideRepository(retrofit = get())
    }

}

fun provideRetrofit(): Retrofit {
    val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
    builder.readTimeout(10, TimeUnit.SECONDS)
    builder.connectTimeout(5, TimeUnit.SECONDS)

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    builder.addInterceptor(loggingInterceptor)

    val client = builder.build()
    return Retrofit.Builder()
        .baseUrl("https://fcm.googleapis.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}


fun provideRepository(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)