package com.egrf.contactsapp.ui.di.modules

import com.egrf.contactsapp.data.network.ContactsApi
import com.google.gson.*
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.OffsetDateTime
import javax.inject.Singleton


@Module
class RestModule {

    companion object {
        const val BASE_URL =
            "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/"
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ContactsApi =
        retrofit.create(ContactsApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideLogger() = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder()
        .registerTypeAdapter(
            OffsetDateTime::class.java,
            JsonDeserializer { json: JsonElement, type: Type?, context: JsonDeserializationContext? ->
                OffsetDateTime.parse(
                    json.asString
                )
            } as JsonDeserializer<OffsetDateTime>)
        .registerTypeAdapter(OffsetDateTime::class.java,
            JsonSerializer { src: OffsetDateTime, typeOfSrc: Type?, context: JsonSerializationContext? ->
                JsonPrimitive(
                    src.toString()
                )
            } as JsonSerializer<OffsetDateTime>)
        .create()

}
