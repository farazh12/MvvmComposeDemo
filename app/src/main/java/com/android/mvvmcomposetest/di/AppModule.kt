package com.android.mvvmcomposetest.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.room.Room
import com.android.mvvmcomposetest.data.local.dao.AppDao
import com.android.mvvmcomposetest.data.local.database.AppDatabase
import com.android.mvvmcomposetest.data.network.api.ApiService
import com.android.mvvmcomposetest.data.repository.LocalRepository
import com.android.mvvmcomposetest.data.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl(): String {
        return "https://run.mocky.io/v3/"
    }

    @Provides
    @Singleton
    fun provideLocalRepository(@ApplicationContext context: Context): LocalRepository {
        return LocalRepository(provideUserDao(provideDatabase(context)))
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): AppDao {
        return database.appDao()
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(@ApplicationContext context: Context): NetworkRepository {
        return NetworkRepository(
            provideAPIService(), isNetworkConnected(context)
        )
    }

    private fun provideAPIService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
        with(okHttpClient) {
            callTimeout(60L, TimeUnit.SECONDS)
            readTimeout(60L, TimeUnit.SECONDS)
            writeTimeout(60L, TimeUnit.SECONDS)
            connectTimeout(60L, TimeUnit.SECONDS)
        }

        val retrofit = Retrofit.Builder().baseUrl(providesBaseUrl()).client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create()).build()

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun isNetworkConnected(@ApplicationContext context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork != null) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            var result = -1
            if (networkCapabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                result = NetworkCapabilities.TRANSPORT_CELLULAR
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                result = NetworkCapabilities.TRANSPORT_WIFI
            }

            return when (result) {
                NetworkCapabilities.TRANSPORT_WIFI, NetworkCapabilities.TRANSPORT_BLUETOOTH, NetworkCapabilities.TRANSPORT_CELLULAR -> true
                else -> false
            }
        } else return false
    }
}
