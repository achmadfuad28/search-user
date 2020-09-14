package com.achmadfuad.searchuser.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.achmadfuad.searchuser.domain.entities.UserItemResult
import com.achmadfuad.searchuser.domain.usecase.SearchUsersInteractor
import com.achmadfuad.searchuser.presentation.paging.HomeDataFactory
import com.achmadfuad.searchuser.presentation.paging.HomeListDataSource
import com.achmadfuad.searchuser.framework.base.BasePagedViewModel

class MainViewModel(private val searchUsersInteractor: SearchUsersInteractor) : BasePagedViewModel() {
    var bTextSearch = ObservableField<String>()

    private var sourceFactory: HomeDataFactory? = null

    var userList: LiveData<PagedList<UserItemResult>>? = null

    fun getUserList(query: String): LiveData<PagedList<UserItemResult>> {
        if (userList == null) {
            getUserListByKeyword(query)
        }
        return userList as LiveData<PagedList<UserItemResult>>
    }

    override fun refresh() {
        sourceFactory?.homeMenuListLiveData?.value?.invalidate()
    }

    override fun retry() {
        sourceFactory?.homeMenuListLiveData?.value?.retryAllFailed()
    }

    private fun getUserListByKeyword(query: String) {
        val pagedListConfig = pagedListConfig(10)
        sourceFactory = homeMenuDataSourceFactory(query)
        userList = LivePagedListBuilder(sourceFactory!!, pagedListConfig)
            .build()
        mNetworkState = Transformations.switchMap(
            sourceFactory!!.homeMenuListLiveData,
            HomeListDataSource::network
        )
        mInitialState = Transformations.switchMap(
            sourceFactory!!.homeMenuListLiveData,
            HomeListDataSource::initial
        )
        observeState()
    }

    fun searchByKeyword(keyword: String) {
        sourceFactory?.apply {
            query = keyword
            create()
            homeMenuListLiveData.value?.invalidate()
        }
    }

    val showLoadingView = ObservableField<Boolean>(true)

    private fun homeMenuDataSourceFactory(query:String): HomeDataFactory {
        return HomeDataFactory(searchUsersInteractor, query)
    }
    fun showLoading() {
        showLoadingView.set(true)
    }
    fun hideLoading() {
        showLoadingView.set(false)
    }
}
