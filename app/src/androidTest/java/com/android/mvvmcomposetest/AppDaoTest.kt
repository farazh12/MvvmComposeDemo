package com.android.mvvmcomposetest

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.android.mvvmcomposetest.data.local.dao.AppDao
import com.android.mvvmcomposetest.data.local.database.AppDatabase
import com.android.mvvmcomposetest.data.local.entities.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var database: AppDatabase
    lateinit var appDao: AppDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).allowMainThreadQueries().build()
        appDao = database.appDao()
    }

    @Test
    fun testInsertUser_shouldInsertUserIntoDatabase() = runBlocking {
        val user = User(1, "Test User", password= "password")
        appDao.insert(user)
        val insertedUser = appDao.getUserById(1)
        assertEquals(user, insertedUser)
    }

    @After
    fun tearDown() {
        database.close()
    }


}