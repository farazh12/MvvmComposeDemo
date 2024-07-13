package com.android.mvvmcomposetest.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.mvvmcomposetest.data.local.dao.AppDao
import com.android.mvvmcomposetest.data.local.entities.Medicine
import com.android.mvvmcomposetest.data.local.entities.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}