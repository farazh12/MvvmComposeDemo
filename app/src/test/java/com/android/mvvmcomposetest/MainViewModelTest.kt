package com.android.mvvmcomposetest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.mvvmcomposetest.data.local.database.AppDatabase
import com.android.mvvmcomposetest.data.local.entities.User
import com.android.mvvmcomposetest.data.network.api.ApiService
import com.android.mvvmcomposetest.data.network.models.ApiResponse
import com.android.mvvmcomposetest.data.network.models.AssociatedDrug
import com.android.mvvmcomposetest.data.network.models.DiabetesItem
import com.android.mvvmcomposetest.data.network.models.Drug
import com.android.mvvmcomposetest.data.network.models.Lab
import com.android.mvvmcomposetest.data.network.models.Medication
import com.android.mvvmcomposetest.data.network.models.MedicationClass
import com.android.mvvmcomposetest.data.network.models.Problem
import com.android.mvvmcomposetest.data.repository.LocalRepository
import com.android.mvvmcomposetest.data.repository.NetworkRepository
import com.android.mvvmcomposetest.di.AppModule.providesBaseUrl
import com.android.mvvmcomposetest.ui.activities.main.MainViewModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    lateinit var networkRepository: NetworkRepository

    lateinit var localRepository: LocalRepository


    private lateinit var viewModel: MainViewModel

    fun getApiService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
        with(okHttpClient) {
            callTimeout(60L, TimeUnit.SECONDS)
            readTimeout(60L, TimeUnit.SECONDS)
            writeTimeout(60L, TimeUnit.SECONDS)
            connectTimeout(60L, TimeUnit.SECONDS)
        }
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder().baseUrl(providesBaseUrl()).client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

        return retrofit.create(ApiService::class.java)
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        networkRepository = NetworkRepository(
            getApiService(), true
        )
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).allowMainThreadQueries().build()
        localRepository = LocalRepository(db.appDao())
        viewModel = MainViewModel(networkRepository, localRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun fetchMedicines_success() = runTest {
        `when`(networkRepository.fetchAllMedicines()).thenReturn(flow {
            emit(
                ApiResponse(
                    problems = listOf(
                        Problem(
                            Diabetes = listOf(
                                DiabetesItem(
                                    medications = listOf(
                                        Medication(
                                            medicationsClasses = listOf(
                                                MedicationClass(
                                                    className = listOf(
                                                        Drug(
                                                            associatedDrug = listOf(
                                                                AssociatedDrug(
                                                                    "asprin",
                                                                    "",
                                                                    "500 mg"
                                                                )
                                                            ), associatedDrug2 = listOf(
                                                                AssociatedDrug(
                                                                    "somethingElse",
                                                                    "",
                                                                    "500 mg"
                                                                )
                                                            )
                                                        )
                                                    ), className2 = listOf(
                                                        Drug(
                                                            associatedDrug = listOf(
                                                                AssociatedDrug(
                                                                    "asprin",
                                                                    "",
                                                                    "500 mg"
                                                                )
                                                            ), associatedDrug2 = listOf(
                                                                AssociatedDrug(
                                                                    "somethingElse",
                                                                    "",
                                                                    "500 mg"
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    ), labs = listOf(Lab("missing_value"))
                                )
                            ), Asthma = emptyList()
                        )
                    )
                )
            )
        })

        viewModel.fetchMedicines()

        advanceUntilIdle()

        val result = viewModel.medicines.value
        assertTrue(result.isNotEmpty())
        assertEquals(result[0].name, "asprin")
        assertEquals(result[1].name, "somethingElse")
    }

    @Test
    fun findMedicine_success() = runTest {
        `when`(networkRepository.fetchAllMedicines()).thenReturn(flow {
            emit(
                ApiResponse(
                    problems = listOf(
                        Problem(
                            Diabetes = listOf(
                                DiabetesItem(
                                    medications = listOf(
                                        Medication(
                                            medicationsClasses = listOf(
                                                MedicationClass(
                                                    className = listOf(
                                                        Drug(
                                                            associatedDrug = listOf(
                                                                AssociatedDrug(
                                                                    "asprin",
                                                                    "",
                                                                    "500 mg"
                                                                )
                                                            ), associatedDrug2 = listOf(
                                                                AssociatedDrug(
                                                                    "somethingElse",
                                                                    "",
                                                                    "500 mg"
                                                                )
                                                            )
                                                        )
                                                    ), className2 = listOf(
                                                        Drug(
                                                            associatedDrug = listOf(
                                                                AssociatedDrug(
                                                                    "asprin",
                                                                    "",
                                                                    "500 mg"
                                                                )
                                                            ), associatedDrug2 = listOf(
                                                                AssociatedDrug(
                                                                    "somethingElse",
                                                                    "",
                                                                    "500 mg"
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    ), labs = listOf(Lab("missing_value"))
                                )
                            ), Asthma = emptyList()
                        )
                    )
                )
            )
        })

        viewModel.fetchMedicines()

        advanceUntilIdle()

        val result = viewModel.findMedicine("asprin")
        assertNotNull(result)
        assertEquals(result?.name, "asprin")
    }

    @Test
    fun insertUser_success() = runTest {
        val user = User(1, "name", "email")

        doNothing().`when`(localRepository).insertUser(user)

        viewModel.insertUser(user)

        // Simulate delay to allow coroutine to complete
        advanceUntilIdle()

        verify(localRepository).insertUser(user)
    }
}
