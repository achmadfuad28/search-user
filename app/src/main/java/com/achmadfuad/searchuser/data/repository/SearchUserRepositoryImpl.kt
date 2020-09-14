package com.achmadfuad.searchuser.data.repository

import com.achmadfuad.searchuser.data.api.SearchApi
import com.achmadfuad.searchuser.data.common.SearchUserRepository
import com.achmadfuad.searchuser.domain.entities.SearchUsersResult
import com.achmadfuad.searchuser.domain.mapper.SearchUsersMapper
import retrofit2.HttpException
import java.io.IOException

class SearchUserRepositoryImpl(private val service: SearchApi,
                               private val searchUsersMapper: SearchUsersMapper
) : SearchUserRepository {

    override fun searchUsers(query: String?, page: Int): SearchUsersResult {
        val response = service.searchUsers(query, page).execute()
        return if (response.isSuccessful) {
            searchUsersMapper.transform(response.body()!!)
        } else {
            throw IOException(response.message(), HttpException(response))
        }


    }
}