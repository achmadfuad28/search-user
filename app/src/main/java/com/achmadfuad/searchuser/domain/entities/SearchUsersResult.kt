package com.achmadfuad.searchuser.domain.entities


class SearchUsersResult{
    var data: List<UserItemResult>? = null
    var lastPage: Boolean = false
    var count: Int? = null
}