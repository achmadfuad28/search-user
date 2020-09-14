package com.achmadfuad.searchuser.presentation.paging

import com.achmadfuad.searchuser.domain.entities.ListFilter
import com.achmadfuad.searchuser.domain.entities.UserItemResult
import com.achmadfuad.searchuser.domain.usecase.SearchUsersInteractor
import com.achmadfuad.searchuser.framework.base.BasePageKeyedDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeListDataSource(private val searchUsersInteractor: SearchUsersInteractor, private val query: String)
    : BasePageKeyedDataSource<UserItemResult>() {

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserItemResult>) {
        GlobalScope.launch {
            try {
                prepareLoadAfter()

                val filter = ListFilter()
                filter.pageNo = params.key
                val item = searchUsersInteractor.executeAsync(SearchUsersInteractor.Params(query, filter.pageNo))

                loadAfterSuccess()

                val adjacentPageKey = if (item.lastPage) null else filter.pageNo + 1
                callback.onResult(item.data!!, adjacentPageKey)
            } catch (e: Exception) {
                loadAfterError(params, callback, e)
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, UserItemResult>) {
        GlobalScope.launch {
            try {
                prepareLoadInitial()

                val filter = ListFilter()
                filter.pageNo = 1
                val result = searchUsersInteractor.executeAsync(SearchUsersInteractor.Params(query, 1))

                if (result.data.isNullOrEmpty()) {
                    loadInitialEmpty()
                } else {
                    loadInitialSuccess()
                    val nextPageKey = if (result.lastPage) null else filter.pageNo + 1
                    callback.onResult(result.data!!, null, nextPageKey)
                }
            } catch (e: Exception) {
                loadInitialError(params, callback, e)
            }
        }
    }
}