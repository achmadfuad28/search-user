package com.achmadfuad.searchuser.domain.usecase

import com.achmadfuad.searchuser.data.common.SearchUserRepository
import com.achmadfuad.searchuser.domain.entities.SearchUsersResult

class SearchUsersInteractor(private val repository: SearchUserRepository) :
        BaseUsecase<SearchUsersInteractor.Params, SearchUsersResult>() {

    override fun execute(params: Params?): SearchUsersResult {
        val query = params?.query
                ?: throw IllegalArgumentException("missing params: {${Params::query}}")
        val page = params.page
        return repository.searchUsers(query, page)
    }

    data class Params(val query: String, val page: Int)
}