package com.android.mvvmcomposetest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.mvvmcomposetest.data.local.entities.User
import com.android.mvvmcomposetest.data.network.models.*
import com.android.mvvmcomposetest.data.repository.LocalRepository
import com.android.mvvmcomposetest.data.repository.NetworkRepository
import com.android.mvvmcomposetest.ui.activities.main.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Inject
    lateinit var networkRepository: NetworkRepository

    @Inject
    lateinit var localRepository: LocalRepository


    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(networkRepository, localRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun fetchMedicines_success() = runTest {
        `when`(networkRepository.fetchAllMedicines()).thenReturn(
            flow {
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
                                                                    AssociatedDrug("asprin", "", "500 mg")
                                                                ),
                                                                associatedDrug2 = listOf(
                                                                    AssociatedDrug("somethingElse", "", "500 mg")
                                                                )
                                                            )
                                                        ),
                                                        className2 = listOf(
                                                            Drug(
                                                                associatedDrug = listOf(
                                                                    AssociatedDrug("asprin", "", "500 mg")
                                                                ),
                                                                associatedDrug2 = listOf(
                                                                    AssociatedDrug("somethingElse", "", "500 mg")
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        ),
                                        labs = listOf(Lab("missing_value"))
                                    )
                                ),
                                Asthma = emptyList()
                            )
                        )
                    )
                )
            }
        )

        viewModel.fetchMedicines()

        advanceUntilIdle()

        val result = viewModel.medicines.value
        assertTrue(result.isNotEmpty())
        assertEquals(result[0].name, "asprin")
        assertEquals(result[1].name, "somethingElse")
    }

    @Test
    fun findMedicine_success() = runTest {
        `when`(networkRepository.fetchAllMedicines()).thenReturn(
            flow {
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
                                                                    AssociatedDrug("asprin", "", "500 mg")
                                                                ),
                                                                associatedDrug2 = listOf(
                                                                    AssociatedDrug("somethingElse", "", "500 mg")
                                                                )
                                                            )
                                                        ),
                                                        className2 = listOf(
                                                            Drug(
                                                                associatedDrug = listOf(
                                                                    AssociatedDrug("asprin", "", "500 mg")
                                                                ),
                                                                associatedDrug2 = listOf(
                                                                    AssociatedDrug("somethingElse", "", "500 mg")
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        ),
                                        labs = listOf(Lab("missing_value"))
                                    )
                                ),
                                Asthma = emptyList()
                            )
                        )
                    )
                )
            }
        )

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
