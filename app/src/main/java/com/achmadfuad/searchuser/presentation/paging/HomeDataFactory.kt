package com.achmadfuad.searchuser.presentation.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.achmadfuad.searchuser.domain.entities.UserItemResult
import com.achmadfuad.searchuser.domain.usecase.SearchUsersInteractor
import com.achmadfuad.searchuser.framework.base.BaseDataSourceFactory

class HomeDataFactory(
    private val searchUsersInteractor: SearchUsersInteractor, var query: String) : BaseDataSourceFactory<UserItemResult>() {

    val homeMenuListLiveData = MutableLiveData<HomeListDataSource>()
    override fun createDataSource(): DataSource<Int, UserItemResult> {
        val dataSource = HomeListDataSource(searchUsersInteractor, query)
        homeMenuListLiveData.postValue(dataSource)
        return dataSource
    }
}