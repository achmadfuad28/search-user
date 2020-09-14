package com.achmadfuad.searchuser.data.common

import com.achmadfuad.searchuser.domain.entities.SearchUsersResult


interface SearchUserRepository {

    /**
     * search user in Github
     *
     * @query[q] The query used to retrieve users data
     * @return [SearchUsersResult]
     * @throws [Exception]
     */
    @Throws(Exception::class)
    fun searchUsers(query: String?, page: Int): SearchUsersResult

}