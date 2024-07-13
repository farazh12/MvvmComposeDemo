package com.android.mvvmcomposetest.data.repository

import com.android.mvvmcomposetest.data.local.dao.AppDao
import com.android.mvvmcomposetest.data.local.entities.User
import javax.inject.Inject


class LocalRepository @Inject constructor(private val appDao: AppDao) {
    suspend fun insertUser(user: User) {
        appDao.insert(user)
    }

    // TODO: get room db from di and run crud operations from viewmodel
}
